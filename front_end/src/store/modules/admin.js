import {
    addManagerAPI,
    bindHotelAndManagerAPI,
    deleteHotelAPI,
    deleteUserAPI,
    getManagerListAPI,
    getMarketerListAPI
} from '@/api/admin';
import {getUnboundHotelAPI} from '@/api/hotel';
import {addMarketerAPI} from '@/api/market';

const admin = {
    state: {
        hotelManagerId: 0,
        unboundHotelList: [],
        managerList: [],
        marketerList: [],
        addManagerModalVisible: false,
        addHotelToManagerVisible: false,
        addMarketerModalVisible: false,
        addManagerParams: {
            email: '',
            password: '',
            username: '',
        },
        addMarketerParams: {
            email: '',
            password: '',
            username: '',
        },
        bindHotelAndManagerParams: {
            hotelId: '',
            managerId: '',
        },
    },
    mutations: {
        set_hotelManagerId: function (state, data) {
            state.hotelManagerId = data;
        },
        set_managerList: function (state, data) {
            state.managerList = data;
        },
        set_marketerList: function (state, data) {
            state.marketerList = data;
        },
        set_unboundHotelList: function (state, data) {
            state.unboundHotelList = data;
        },
        set_addManagerModalVisible: function (state, data) {
            state.addManagerModalVisible = data;
        },
        set_addHotelToManagerVisible: function (state, data) {
            state.addHotelToManagerVisible = data;
        },
        set_addMarketerModalVisible: function (state, data) {
            state.addMarketerModalVisible = data;
        },
        set_addManagerParams: function (state, data) {
            state.addManagerParams = {
                ...state.addManagerParams,
                ...data,
            };
        },
        set_addMarketerParams: function (state, data) {
            state.addMarketerParams = {
                ...state.addMarketerParams,
                ...data
            };
        },
        set_bindHotelAndManagerParams: function (state, data) {
            state.bindHotelAndManagerParams = {
                ...state.bindHotelAndManagerParams,
                ...data,
            };
        },
    },
    actions: {
        getUnboundHotelList: async ({commit, dispatch}) => {
            const res = await getUnboundHotelAPI();
            console.log('123')
            if (res.success) {
                commit('set_unboundHotelList', res.content);
            }
        },
        getMarketerList: async ({commit}) => {
            const res = await getMarketerListAPI();
            if (res.success) {
                commit('set_marketerList', res.content);
            }
        },
        getManagerList: async ({commit}) => {
            const res = await getManagerListAPI();
            if (res.success) {
                commit('set_managerList', res.content);
            }
        },
        addManager: async ({state, commit, dispatch}) => {
            const res = await addManagerAPI(state.addManagerParams);
            if (res.success) {
                commit('set_addManagerParams', {
                    email: '',
                    password: '',
                    username: '',
                });
                commit('set_addManagerModalVisible', false);
                dispatch('getManagerList');
            }
        },
        addMarketer: async ({state, commit, dispatch}) => {
            const res = await addMarketerAPI(state.addMarketerParams);
            if (res.success) {
                commit('set_addMarketerParams', {
                    email: '',
                    password: '',
                    username: '',
                });
                commit('set_addMarketerModalVisible', false);
                dispatch('getMarketerList');
            }
        },
        bindHotelAndManager: async ({state, commit, dispatch}) => {
            const res = await bindHotelAndManagerAPI(state.bindHotelAndManagerParams);
            if (res.success) {
                commit('set_bindHotelAndManagerParams', {
                    hotelId: '',
                    managerId: '',
                });
                dispatch('getHotelListByManager', state.hotelManagerId);
                dispatch('getUnboundHotelList');
            }
        },
        deleteUser: async ({state, dispatch}, data) => {
            const res = await deleteUserAPI(data);
            if (res.success) {
                dispatch('getManagerList');
                dispatch('getMarketerList');
            }
        },
        deleteHotel: async ({state, dispatch}, data) => {
            const res = await deleteHotelAPI(data);
            if (res.success) {
                dispatch('getHotelList');
            }
        },

    },
};
export default admin;
