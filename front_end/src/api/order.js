import {axios} from '@/utils/request';

const api = {
    orderPre: '/api/order',
};

export function reserveHotelAPI(data) {
    return axios({
        url: `${api.orderPre}/new`,
        method: 'POST',
        data,
    });
}

export function getOrderByManagerAPI(data) {
    return axios({
        url: `${api.orderPre}/by-manager?id=${data.id}`,
        method: 'GET'
    });
}

export function getAllOrdersAPI() {
    return axios({
        url: `${api.orderPre}/`,
        method: 'GET',
    });
}

export function getUserOrdersAPI(data) {
    return axios({
        url: `${api.orderPre}/by-user?id=${data.userId}`,
        method: 'GET',
    });
}

export function cancelOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/rm?id=${orderId}`,
        method: 'POST',
    });
}

export function errorOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/expire?id=${orderId}`,
        method: 'POST',
        orderId,
    })
}

export function getCreditTransAPI(data){
    return axios({
        url: `${api.orderPre}/credit-trans?id=`+data,
        method:'GET',
    })
}

export function availableOrdersAPI(data){
    return axios({
        url: `${api.orderPre}/available?id=${data.userId}`,
        method:'GET',
    })
}
