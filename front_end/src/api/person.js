import {axios} from '@/utils/request';

const api = {
    personPre: '/api/person',
};

export function addUserInfoAPI(data) {
    return axios({
        url: `${api.personPre}/new`,
        method: 'POST',
        data,
    });
}

export function getPersonListByUserIdAPI(data) {
    return axios({
        url: `${api.personPre}/by-user?id=${data}`,
        method: 'GET',
    });
}

export function userAllPersonsAPI(userId) {
    return axios({
        url: `${api.personPre}/by-user?id=${userId}`,
        method: 'GET',
    });
}

export function deletePersonAPI(id) {
    return axios({
        url: `${api.personPre}/rm`,
        method: 'POST',
        params: {id: id},
    });
}

export function updatePersonInfoAPI(data) {
    return axios({
        url: `${api.personPre}/`,
        method: 'PUT',
        data,
    });
}

