import {axios} from '@/utils/request';

const api = {
    commentPre: '/api/comment',
};

export function orderCommentAPI(data) {
    return axios({
        url: `${api.commentPre}/`,
        method: 'POST',
        data,
    });
}
