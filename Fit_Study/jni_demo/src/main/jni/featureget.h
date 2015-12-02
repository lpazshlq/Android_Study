/* 
 * File:   serverproc.h
 * Author: chenzqng
 *
 * Created on 2015年6月29日, 上午10:39
 */

#ifndef SERVERPROC_H
#define	SERVERPROC_H

#ifdef	__cplusplus
extern "C" {
#endif

    
    


#include <stdlib.h>
#include <stdio.h>
#include <stdlib.h>
#include<math.h>

#include "filter.h"

#define FILTER_NUM 5  /*滤波器*/
#define WIN_SIZE 8000 /*特征值窗采样数*/
#define WIN_STEP 500 /*特征值窗步进*/
    
#define BOOLSIZE 16*40

    

typedef struct _featureget
{
    discrete_filter filter[FILTER_NUM];
    int64_t input_sample_num; //输入的采样数
    int64_t sample_8k_num; //转成8K的采样数
    double feat_win[FILTER_NUM][WIN_SIZE]; //计算特征值的采样窗
    int feat_win_pos;//feat_win窗当前填满的位置
    float currval[FILTER_NUM];/*当前特征值*/
    float diffval[FILTER_NUM];/*差分后的特征值*/
    char boolval[FILTER_NUM][BOOLSIZE];/*向服务器发送的音频特征，40秒钟的数据*/
    int boolcurrsize;/*当前已经完成bool特征值*/
    int sendflag;/*为1的时候开始填充boolval*/
}featureget;


    int init_featureget (featureget *pgetter);
    int input_pcm (featureget *pgetter, char* pdata, int datasize, int rate, int channelnum);
    
    int input_feature (featureget *pgetter);
    int send_feature();
    int receive_result();






#ifdef	__cplusplus
}
#endif

#endif	/* SERVERPROC_H */

