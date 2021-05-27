import {getHotelByIdAPI, getHotelsAPI, searchHotelAPI, updateHotelInfoAPI} from '@/api/hotel';
import {reserveHotelAPI} from '@/api/order';
import {orderMatchCouponsAPI} from '@/api/coupon';
import {getCommentByIdAPI,getHotelHotAPI} from '@/api/hotel';

const hotel = {
    state: {
        annoParams: {},
        hotelList: [],
        hotelInfoParams: {},
        hotelListParams: {
            pageNo: 0,
            pageSize: 12,
        },
        hotelListLoading: true,
        currentHotelId: '',
        currentHotelInfo: {},
        orderModalVisible: false,
        manageHotelVisible: false,
        announcementVisible: false,
        currentOrderRoom: {},
        orderMatchCouponList: [],
        current: {},
        commentList: [],
        commentUser: {},
        orderSuccess: false,
    },
    mutations: {
        set_orderSuccess: function (state, data) {
            state.orderSuccess = data;
        },
        set_commentList: function (state, data) {
            state.commentList = data;
        },
        set_hotelInfoParams: function (state, data) {
            state.hotelInfoParams = {
                ...state.hotelInfoParams,
                ...data,
            };
            state.currentHotelId = state.hotelInfoParams.id;
        },
        set_hotelList: function (state, data) {
            state.hotelList = data;
        },
        set_hotelListParams: function (state, data) {
            state.hotelListParams = {
                ...state.hotelListParams,
                ...data,
            };
        },
        set_hotelListLoading: function (state, data) {
            state.hotelListLoading = data;
        },
        set_currentHotelId: function (state, data) {
            state.currentHotelId = data;
        },
        set_currentHotelInfo: function (state, data) {
            state.currentHotelInfo = {
                ...state.currentHotelInfo,
                ...data,
            };
        },
        set_orderModalVisible: function (state, data) {
            state.orderModalVisible = data;
        },
        set_currentOrderRoom: function (state, data) {
            state.currentOrderRoom = {
                ...state.currentOrderRoom,
                ...data,
            };
        },
        set_orderMatchCouponList: function (state, data) {
            state.orderMatchCouponList = data;
        },
        set_manageHotelVisible: function (state, data) {
            state.manageHotelVisible = data;
            console.log(state.manageHotelVisible);
        },
        set_current: function (state, data) {
            state.current = {
                ...state.current,
                ...data,
            };
        },
        set_announcementVisible: function (state, data) {
            state.announcementVisible = data;
        },
    },

    actions: {
        searchToUpdateHotelList: async ({commit, state}, data) => {
            const res = await searchHotelAPI(data);
            commit('set_hotelList', res.content);
        },
        getHotelList: async ({commit, state}) => {
            const res = await getHotelsAPI();
            if (res.success) {
                commit('set_hotelList', res.content);
                commit('set_hotelListLoading', false);
            }
        },
        getHotelById: async ({commit, state}) => {
            const res = await getHotelByIdAPI(state.currentHotelId);
            if (res.success) {
                commit('set_currentHotelInfo', res.content);
            }
        },
        addOrder: async ({state, commit, dispatch}, data) => {
            const res = await reserveHotelAPI(data);
            if (res.success) {
                commit('set_orderModalVisible', false);
                commit('set_orderSuccess', true);
            }
        },
        getOrderMatchCoupons: async ({state, commit}, data) => {
            const res = await orderMatchCouponsAPI(data);
            if (res.success) {
                commit('set_orderMatchCouponList', res.content);
            }
        },
        updateHotelInfo: async ({state, dispatch}) => {
            const res = await updateHotelInfoAPI(state.hotelInfoParams);
            if (res.success) {
                dispatch('getHotelById');
            }
        },
        getCommentList: async ({state, commit}) => {
            const res = await getCommentByIdAPI(parseInt(state.currentHotelId));
            if (res.success) {
                commit('set_commentList', res.content);
            }
        },
        getHotelHot:async ({state,commit})=>{
            const res = await getHotelHotAPI();
            if (res.success){
                commit('set_hotelList',res.content);
            }
        },
        getHotelCold:async ({state,commit})=>{
            const res = await getHotelHotAPI();
            if (res.success){
                commit('set_hotelList',res.content.reverse());
            }
        }
    },
};

export default hotel;
