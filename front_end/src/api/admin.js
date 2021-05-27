import {axios} from '@/utils/request';

const api = {
    adminPre: '/api/admin',
};

export function getManagerListAPI() {
    return axios({
        url: `${api.adminPre}/managers`,
        method: 'GET',
    });
}

export function getMarketerListAPI() {
    return axios({
        url: `${api.adminPre}/marketers`,
        method: 'GET',
    });
}

export function addManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/new-manager`,
        method: 'POST',
        data,
    });
}

export function bindHotelAndManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/bind?hotelId=${data.hotelId}&managerId=${data.managerId}`,
        method: 'PUT',
    });
}

export function deleteUserAPI(data) {
    return axios({
        url: `${api.adminPre}/rm-user?id=` + data,
        method: 'POST',
    });
}

export function deleteHotelAPI(data) {
    return axios({
        url: `${api.adminPre}/rm-hotel?id=` + data,
        method: 'POST',
    });
}

