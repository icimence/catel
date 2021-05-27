import {axios} from '@/utils/request';

const api = {
    hotelPre: '/api/hotel',

    commentPre: '/api/comment',
};

export function getHotelsAPI() {
    return axios({
        url: `${api.hotelPre}/all`,
        method: 'get',
    });
}

export function getHotelByIdAPI(data) {
    return axios({
        url: `${api.hotelPre}/detail?id=${data}`,
        method: 'GET',
    });
}

export function getUnboundHotelAPI() {
    return axios({
        url: `${api.hotelPre}/unbound`,
        method: 'GET',
    });
}

export function updateHotelInfoAPI(data) {
    return axios({
        url: `${api.hotelPre}/info`,
        method: 'PUT',
        data,
    });
}

export function searchHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/search?keyword=` + data,
        method: 'GET',
        data,
    });
}

export function getCommentByIdAPI(data) {
    return axios({
        url: `${api.commentPre}/by-hotel?id=` + data,
        method: 'GET',
    });
}

export function getHotelHotAPI() {
    return axios({
        url: `${api.hotelPre}/hot`,
        method: 'GET',
    });
}
