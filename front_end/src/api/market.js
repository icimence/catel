import {axios} from '@/utils/request';

const api = {
    marketerPre: '/api/market',
};

export function addMarketerAPI(data) {
    return axios({
        url: `${api.marketerPre}/new`,
        method: 'POST',
        data,
    });
}
