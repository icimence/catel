import {axios} from '@/utils/request';

const api = {
    couponPre: '/api/coupon',
};

export function addCouponAPI(data) {
    return axios({
        url: `${api.couponPre}/new`,
        method: 'POST',
        data,
    });
}

export function hotelAllCouponsAPI(hotelId) {
    return axios({
        url: `${api.couponPre}/by-hotel`,
        method: 'GET',
        params: {hotelId: hotelId},
    });
}

export function orderMatchCouponsAPI(data) {
    console.log("显示订单信息：");
    console.log(data);
    return axios({
        url: `${api.couponPre}/get-matched`,
        method: 'POST',
        data,
    });
}

export function getAllPlatformCouponAPI(data) {
    return axios({
        url: `${api.couponPre}/global`,
        method: 'GET',
        data,
    });
}

export function deleteCouponAPI(data) {
    return axios({
        url: `${api.couponPre}/rm?id=${data}`,
        method: 'POST',
        data,
    });
}
