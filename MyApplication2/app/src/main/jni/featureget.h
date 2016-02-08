/* 
 * File:   featureget.h
 * Author: chenzqng
 *
 * Created on 2015年6月29日, 上午10:39
 */

#ifndef FEATUREGET_H
#define	FEATUREGET_H

#ifdef	__cplusplus
extern "C" {
#endif

    
    


#include <stdlib.h>
#include <stdio.h>
#include <stdlib.h>
#include<math.h>

    

    
    
    
#define FILTER_POOL_SIZE 32
#define MAX_FILTER_LEVEL FILTER_POOL_SIZE

#define FILTER_NUM 5  /*滤波器*/
#define WIN_SIZE 8000 /*特征值窗采样数*/
#define WIN_STEP 500 /*特征值窗步进*/
    
#define BOOLSIZE 16

    
    typedef struct _discrete_filter {
        double b[MAX_FILTER_LEVEL]; //b参数数组
        int b_num; //b参数数量
        double a[MAX_FILTER_LEVEL]; //a参数数组
        int a_num; //a参数数量
        
        
        
        double y_pool[FILTER_POOL_SIZE];
        int curr_y;
        double x_pool[FILTER_POOL_SIZE];
        int curr_x;
        
    } discrete_filter;
    

typedef struct _featureget
{
    discrete_filter filter[FILTER_NUM];
    int64_t input_sample_num; //输入的采样数
    int64_t sample_8k_num; //转成8K的采样数
    double feat_win[FILTER_NUM][WIN_SIZE]; //计算特征值的采样窗
    int feat_win_pos;//feat_win窗当前填满的位置

    
    float currvals[FILTER_NUM][5]; /*当前特征值，保留最新的5个作为核*/
    float slopeval[FILTER_NUM]; /*冲坡的特征值*/
    float peakval[FILTER_NUM]; /*波峰的特征值*/
    float hatval[FILTER_NUM]; /*墨西哥帽的特征值*/
    
    char boolval[FILTER_NUM][BOOLSIZE];/*累积1秒钟的数据，准备更新发送缓冲*/
    int boolcurrsize;/*当前已经完成bool特征值*/
    char boolvalsend[FILTER_NUM][BOOLSIZE];/*发送缓冲*/
    int sendflag;/*1:boolvalsend已发送 0:boolvalsend未发送*/
}featureget;
    
typedef struct _returnval
{
    int status; //1：成功获得参数  0：未获得参数
    char boolvalsend[FILTER_NUM][BOOLSIZE]; //音频参数
}returnval;


    int init_featureget ();
    int input_pcm ( char* pdata, int datasize, int rate, int channelnum);
    
    int input_feature ();
    returnval get_feature();

    
    
    double input_filter(discrete_filter *pfilter, double x);
    int init_discrete_filter(discrete_filter *pfilter, double *b, int b_num, double *a, int a_num);
    int reset_filter(discrete_filter *pfilter);






#ifdef	__cplusplus
}
#endif

#endif	/* FEATUREGET_H */

