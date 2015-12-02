#ifndef FILTER_H
#define	FILTER_H


#define FILTER_POOL_SIZE 32 
#define MAX_FILTER_LEVEL FILTER_POOL_SIZE

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

double input_filter(discrete_filter *pfilter, double x);
int init_discrete_filter(discrete_filter *pfilter, double *b, int b_num, double *a, int a_num);
int reset_filter(discrete_filter *pfilter);

#endif