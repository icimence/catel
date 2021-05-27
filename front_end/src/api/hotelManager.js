import {axios} from '@/utils/request';

const api = {
    hotelPre: '/api/hotel',
    roomPre: '/api/room',
};

export function addRoomAPI(data) {
    return axios({
        url: `${api.roomPre}/new`,
        method: 'POST',
        data,
    });
}

export function addHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/new`,
        method: 'POST',
        data,
    });
}

export function getHotelByManagerAPI(data) {
    return axios({
        url: `${api.hotelPre}/by-manager?id=${data.id}`,
        method: 'GET',
    });
}

export function getAvailableRoomAPI(data) {
    return axios({
        url: `${api.roomPre}/available?id=` + data,
        method: 'GET',
    })
}

export function getRoomListAPI(data) {
    return axios({
        url: `${api.roomPre}/by-hotel?id=` + data,
        method: 'GET',
    })
}

export function deleteRoomAPI(data) {
    return axios({
        url: `${api.roomPre}/rm-room?id=` + data,
        method: 'POST',
    })
}
