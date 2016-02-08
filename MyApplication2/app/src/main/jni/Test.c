#include <jni.h>
#include <stddef.h>
#include "featureget.h"

static double IIR_B[5][7] = {
        {1.4210854715202e-16, 2.99149005633775e-05, -6.06060828489678e-05, 2.32879052967611e-06, 5.75010560137275e-05, -2.91386644754015e-05, 0}
        ,
        {-4.2632564145606e-16, 0.000141290219634175, -0.000288710921554762, 1.83871487872884e-05, 0.000264196506546496, -0.000135162995288021, 0}
        ,
        {-2.48689957516035e-16, 0.000645978211445738, -0.00133832192481761, 0.000138740505064965, 0.0011534451963106, -0.000599849878985739, 0}
        ,
        {-2.8421709430404e-16, 0.00261316693602127, -0.00553425114022261, 0.000894160175083613, 0.00434765165153592, -0.00232208872385505, 0}
        ,
        {-8.24229573481716e-16, 0.00712590216711292, -0.0162631285514376, 0.00381097290603952, 0.011316188261071, -0.0061797309008633, 0}
};

static double IIR_A[5][7] = {
        {1, -5.82579649819104, 14.235161947866, -18.6714058401126, 13.8645738465348, -5.52642146978216, 0.923919860801024}
        ,
        {1, -5.59505326361374, 13.303237103346, -17.1867185593556, 12.720825083584, -5.11590065597884, 0.874345671851548}
        ,
        {1, -5.01313777417718, 11.1546887868188, -13.9525186953913, 10.3355470155386, -4.30394202262262, 0.795553315242415}
        ,
        {1, -3.56893023690053, 6.86639915871998, -7.93549491873286, 6.03101959939817, -2.75279624257162, 0.677761443425244}
        ,
        {1, -0.385905923474118, 2.39464947211909, -0.607824881613297, 1.92645258237897, -0.245052913716946, 0.516384345938306}
};


static float loudpara[] = {0.09, 0.22, 0.45, 0.45, 0.38}; /*子带加权*/


static featureget getter;
static featureget *pgetter = &getter;


int init_featureget ()
{

    memset(pgetter,0,sizeof(featureget));

    int i = 0;

    //初始化滤波器
    for (i = 0; i < FILTER_NUM; i++)
    {
        init_discrete_filter(&pgetter->filter[i], IIR_B[i], 7, IIR_A[i], 7);
    }

    return 0;
}



int input_pcm ( char* pdata, int datasize, int rate, int channelnum)
{


    if (channelnum == 0 || pdata == NULL)
    {
        return -1;
    }


    double k = (double) rate / 8000; //下变换因子
    int i = 0;
    int samplenum = datasize / 2 / channelnum;
    for (i = 0; i < samplenum; i++)
    {
        pgetter->input_sample_num++;
        int64_t temp = pgetter->input_sample_num / k;
        if (temp > pgetter->sample_8k_num) //下采样
        {
            pgetter->sample_8k_num = temp;
            int n;
            double valsum = 0;
            short *pval = pdata + i * 2 * channelnum;
            for (n = 0; n < channelnum; n++)
            {
                valsum += *pval;
            }
            valsum = valsum / channelnum / 32768; //声道采样混合,-1~1归一化


            //过5组滤波器
            for (n = 0; n < FILTER_NUM; n++)
            {
                double y = input_filter(&pgetter->filter[n], valsum);
                pgetter->feat_win[n][pgetter->feat_win_pos] = y;
            }



            if (pgetter->feat_win_pos + 1 == WIN_SIZE)
            {

                for (n = 0; n < FILTER_NUM; n++)
                {
                    int num = 0;
                    double sum = 0;
                    for (num = 0; num < WIN_SIZE; num++)
                    {
                        if (pgetter->feat_win[n][num] < 0)
                        {
                            sum = sum - pgetter->feat_win[n][num];
                        }
                        else
                        {
                            sum = sum + pgetter->feat_win[n][num];
                        }
                    }

                    //psample->pfeat_obj->val[n][pos] = sum;

                    if (n == 0)
                    {
                        //printf("hererere %f\n", sum);
                        //fprintf(pfilet,"%f\n",sum);
                        //fflush(pfilet);
                    }
                    //sum = sum * loudpara[n];
                    //保存最新的5个值作为核
                    memmove(&pgetter->currvals[n][1], &pgetter->currvals[n][0], sizeof (float)*4);
                    pgetter->currvals[n][0] = sum;
                    //pgetter->slopeval[n] = pgetter->currvals[n][3] - pgetter->currvals[n][1] + pgetter->currvals[n][4] - pgetter->currvals[n][0];
                    pgetter->slopeval[n] = pgetter->currvals[n][3] - pgetter->currvals[n][1];
                    pgetter->peakval[n] = pgetter->currvals[n][2] * 4 - pgetter->currvals[n][1] - pgetter->currvals[n][3] \
                    - pgetter->currvals[n][0] - pgetter->currvals[n][4];
                    pgetter->hatval[n] = 0 - pgetter->currvals[n][1] - pgetter->currvals[n][3] \
                    + pgetter->currvals[n][0] +  pgetter->currvals[n][4];

                    memmove(pgetter->feat_win[n], pgetter->feat_win[n] + WIN_STEP, sizeof (double)*(WIN_SIZE - WIN_STEP));

                }
                //printf("hererere %f %f %f %f %f \n",pgetter->diffval[0],pgetter->diffval[1],pgetter->diffval[2],pgetter->diffval[3],pgetter->diffval[4]);

                input_feature(pgetter);



                pgetter->feat_win_pos = WIN_SIZE - WIN_STEP; //窗内数据满后计算意思，然后步进

            }
            else
            {
                pgetter->feat_win_pos++;
            }

        }
    }

    //fflush(pfile);
    return 0;
}

int input_feature ()
{
    int i=0;
    int currpos =BOOLSIZE - pgetter->boolcurrsize - 1;
    for (i=0; i<FILTER_NUM;i++) {
        //slopeval
        if (pgetter->slopeval[i] >= 0) {
            pgetter->boolval[i][currpos] = 1;
        }
        else
        {
            pgetter->boolval[i][currpos] = 0;
        }

#if 1
        //peakval
        if (pgetter->peakval[i] >= 0) {
            pgetter->boolval[i][currpos] |= 2;
        }

        //hatval
        if (pgetter->hatval[i] >= 0) {
            pgetter->boolval[i][currpos] |= 4;
        }

#endif



    }
    pgetter->boolcurrsize++;

    if (pgetter->boolcurrsize >= BOOLSIZE)//1秒的数据缓冲满了
    {

        if (pgetter->sendflag == 1)
        {
            memcpy(pgetter->boolvalsend, pgetter->boolval,sizeof(pgetter->boolval));
            pgetter->sendflag = 0;
        }
        pgetter->boolcurrsize = 0;
    }


    return 0;

}

returnval get_feature()
{
    returnval ret;
    memset(&ret, 0, sizeof(returnval));
    if (pgetter->sendflag == 0)
    {
        memcpy(ret.boolvalsend, pgetter->boolvalsend, sizeof(ret.boolvalsend));
        ret.status = 1;
        pgetter->sendflag = 1;
    }
    return ret;

}




inline double input_filter(discrete_filter *pfilter, double x)
{


    pfilter->curr_x++;
    if (pfilter->curr_x == FILTER_POOL_SIZE)
    {
        pfilter->curr_x = 0;
    }
    pfilter->x_pool[pfilter->curr_x] = x;


    double y = 0;
    int i;
    int ind_x = pfilter->curr_x;
    int ind_y = pfilter->curr_y;
    for (i = 0; i < pfilter->b_num; i++)
    {
        if (ind_x < 0)
        {
            ind_x = FILTER_POOL_SIZE - 1;
        }


        y += pfilter->b[i] * pfilter->x_pool[ind_x];
        ind_x--;
    }

    for (i = 1; i < pfilter->a_num; i++)
    {
        if (ind_y < 0)
        {
            ind_y = FILTER_POOL_SIZE - 1;
        }

        y -= pfilter->a[i] * pfilter->y_pool[ind_y];
        ind_y--;
    }

    pfilter->curr_y++;
    if (pfilter->curr_y >= FILTER_POOL_SIZE)
    {
        pfilter->curr_y = 0;
    }

    pfilter->y_pool[pfilter->curr_y] = y;

    return y;




}

int init_discrete_filter(discrete_filter *pfilter, double *b, int b_num, double *a, int a_num)
{
    if (pfilter == NULL || b == NULL || a == NULL)
    {
        return -1;
    }
    memset(pfilter->x_pool, 0, sizeof (pfilter->x_pool));
    memset(pfilter->y_pool, 0, sizeof (pfilter->y_pool));

    memcpy(pfilter->a, a, sizeof (double) * a_num);
    memcpy(pfilter->b, b, sizeof (double) * b_num);
    pfilter->a_num = a_num;
    pfilter->b_num = b_num;

    pfilter->curr_x = 0;
    pfilter->curr_y = 0;

    return 0;

}

int reset_filter(discrete_filter *pfilter)
{
    if (pfilter == NULL)
    {
        return 0;
    }
    pfilter->curr_x = 0;
    pfilter->curr_y = 0;
    memset(pfilter->x_pool, 0, sizeof (pfilter->x_pool));
    memset(pfilter->y_pool, 0, sizeof (pfilter->y_pool));
    return 0;

}



JNIEXPORT void JNICALL
Java_com_lei_android_myapplication_JNI_inputPCM(JNIEnv *env, jclass type, jbyteArray pcm_,
                                                jint datasize, jint rate, jint channelnum) {
    jbyte *pcm = (*env)->GetByteArrayElements(env, pcm_, NULL);

    input_pcm(pcm, datasize, rate, channelnum);

    (*env)->ReleaseByteArrayElements(env, pcm_, pcm, 0);
}

JNIEXPORT void JNICALL
Java_com_lei_android_myapplication_JNI_init_1Featureget(JNIEnv *env, jclass type) {
    init_featureget();
}

JNIEXPORT jbyteArray JNICALL
Java_com_lei_android_myapplication_JNI_getPcmResult(JNIEnv *env, jclass type) {
    returnval result = get_feature();
    if (result.status == 1) {
        /* 将获取的result数据转换成byte数组进行返回 */
        jbyte *b = (jbyte *) result.boolvalsend;
        jbyteArray data = (*env)->NewByteArray(env, 5 * 16);
        (*env)->SetByteArrayRegion(env, data, 0, 5 * 16, b);

        return data;
    }
    return NULL;
}