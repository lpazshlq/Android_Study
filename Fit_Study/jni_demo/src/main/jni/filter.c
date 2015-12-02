#include "filter.h"
#include <stdio.h>
#include <stdlib.h>
#include<math.h>




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