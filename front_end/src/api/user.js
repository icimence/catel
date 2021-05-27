import {axios} from '@/utils/request';

const api = {
    userPre: '/api/user',
};

export function loginAPI(data) {
    return axios({
        url: `${api.userPre}/login`,
        method: 'POST',
        data,
    });
}

export function registerAPI(data) {
    return axios({
        url: `${api.userPre}/new-client`,
        method: 'POST',
        data,
    });
}

export function getUserInfoAPI(id) {
    return axios({
        url: `${api.userPre}/info/?id=`+ id,
        method: 'GET',
    });
}

export function updateUserInfoAPI(data) {
    return axios({
        url: `${api.userPre}/info`,
        method: 'PUT',
        data,
    });
}

export function getCaptchaAPI() {
    return axios({
        url: `${api.userPre}/captcha`,
        method: 'GET',
    });
}

export function upgradeToVIPAPI(data) {
    return axios({
        url: `${api.userPre}/vip`,
        method: 'POST',
        data,
    });
}

export function creditUpAPI(data) {
    return axios({
        url: `${api.userPre}/credit-up`,
        method: 'POST',
        data,
    });
}
