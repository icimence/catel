import router, {resetRouter} from '@/router';
import {removeToken, setToken} from '@/utils/auth';
import {getCaptchaAPI, getUserInfoAPI, loginAPI, registerAPI, updateUserInfoAPI, upgradeToVIPAPI} from '@/api/user';

import {cancelOrderAPI, getUserOrdersAPI,getCreditTransAPI,availableOrdersAPI} from '@/api/order';
import {
    addUserInfoAPI,
    deletePersonAPI,
    getPersonListByUserIdAPI,
    updatePersonInfoAPI,
    userAllPersonsAPI,
} from '@/api/person';
import {orderCommentAPI} from '@/api/comment';
import {getAvailableRoomAPI} from '@/api/hotelManager';

const getDefaultState = () => {
    return {
        availableRoomList: [],
        userInfoParams: {},
        addUserInfoVisible: false,
        captchaParams: {
            answer: '',
            picBase: '',
        },
        userId: '',
        userInfo: {},
        userOrderList: [],
        availableOrderList:[],
        personList: [],
        userInfoList: [],
        orderDetailVisible: false,
        modifyUserInfoListVisible: false,
        currentPersonId: '',
        currentPersonInfo: {},
        upgradeToVIPVisible: false,
        evaluateOrdersVisible: false,
        modifyPasswordVisible: false,
        creditTrans: [],
    };
};
const user = {
    state: getDefaultState(),

    mutations: {
        set_availableRoomList(state,data){
            state.availableRoomList=data;
        },
        set_creditTrans(state,data){
            state.creditTrans=data;
        },
        set_upgradeToVIPVisible(state, data) {
            state.upgradeToVIPVisible = data;
        },
        set_addUserInfoVisible: function (state, data) {
            state.addUserInfoVisible = data;
        },
        set_modifyUserInfoListVisible: function (state, data) {
            state.modifyUserInfoListVisible = data;
        },
        reset_state: function (state) {
            state.token = '',
                state.userId = '',
                state.userInfo = {},
                state.userOrderList = [],
                state.userInfoList = [],
                state.modifyUserInfoListVisible = false,
                state.orderDetailVisible = false;
        },
        set_token: function (state, token) {
            state.token = token;
        },
        set_email: (state, data) => {
            state.email = data;
        },
        set_userId: (state, data) => {
            state.userId = data;
        },
        set_userInfo: (state, data) => {
            state.userInfo = {
                ...state.userInfo,
                ...data,
            };
        },
        set_addUserInfoParams: (state, data) => {
            state.userInfoParams = {
                ...state.userInfoParams,
                ...data,
            };
        },
        set_userOrderList: (state, data) => {
            state.userOrderList = data;
        },
        set_availableOrderList: (state,data)=>{
            state.availableOrderList = data;
        },
        set_personList: (state, data) => {
            state.personList = data;
        },
        set_userInfoList: function (state, data) {
            state.userInfoList = data;
        },
        set_currentPerson: function (state, data) {
            state.current = {
                ...state.current,
                ...data,
            };
        },
        set_currentPersonId: function (state, data) {
            state.currentPersonId = data;
        },
        set_orderDetailVisible: function (state, data) {
            state.orderDetailVisible = data;
        },
        set_captchaParams: (state, data) => {
            state.captchaParams = {
                ...state.captchaParams,
                ...data,
            };
        },
        set_currentPersonInfo: (state, data) => {
            state.currentPersonInfo = {
                ...state.currentPersonInfo,
                ...data,
            };
        },
        set_evaluateOrdersVisible: function (state, data) {
            state.evaluateOrdersVisible = data;
        },
        set_modifyPasswordVisible: function (state, data) {
            state.modifyPasswordVisible = data;
        },
    },

    actions: {
        getAvailableRoom:async ({state,commit},data) =>{
            const res = await getAvailableRoomAPI(data);
            if(res.success){
                commit('set_availableRoomList',res.content);
            }
        },
        getCreditTrans:async ({state,commit})=>{
            const res = await getCreditTransAPI(state.userId);
            if(res.success){
                commit('set_creditTrans',res.content);
            }
        },
        upgradeToVIP: async ({commit, dispatch}, data) => {
            const res = await upgradeToVIPAPI(data);
            dispatch('getUserInfo');
        },
        addUserInfo: async ({commit, state, dispatch}) => {
            const res = await addUserInfoAPI(state.userInfoParams);
            if (res.success) {
                commit('set_addUserInfoVisible', false);
                dispatch('getUserInfoList');
            }
        },
        getUserInfoList: async ({state, commit}) => {
            const res = await userAllPersonsAPI(state.userId);
            if (res.success) {
                commit('set_userInfoList', res.content);
            }
        },
        getPersonById: async ({commit, state}, id) => {
            let res = '';
            for (let i = 0; i < state.userInfoList.length; i++) {
                if (state.userInfoList[i]['id'] === id) {
                    res = state.userInfoList[i];
                    break;
                }
            }
            if (res) {
                commit('set_currentPersonInfo', res);
            }
        },
        deleteUserInfoList: async ({state, dispatch}, id) => {
            const res = await deletePersonAPI(id);
            if (res.success) {
                dispatch('getUserInfoList');
            }
        },
        getCaptcha: async ({commit, state}) => {
            const res = await getCaptchaAPI();
            if (res.success) {
                commit('set_captchaParams', res.content);
            }
        },
        login: async ({dispatch, commit}, userData) => {
            const res = await loginAPI(userData);
            if (res.success) {
                setToken(res.content.id);
                commit('set_userId', res.content.id);
                dispatch('getUserInfo');
                router.push('/hotel/hotelList');
            }
        },
        register: async ({commit}, data) => {
            const res = await registerAPI(data);
        },
        getUserId({state}) {
            return state.userId;
        },
        getPersonList: async ({state, commit}, data) => {
            const res = await getPersonListByUserIdAPI(data);
            commit('set_personList', res.content);
        },
        getUserInfo({state, commit}) {
            return new Promise((resolve, reject) => {
                getUserInfoAPI(state.userId).then(response => {
                    const data = response;
                    if (data.error) {
                        reject('登录已过期，请重新登录');
                    }
                    commit('set_userInfo', data.content);
                    commit('set_userId', data.content.id);
                    resolve(data.content);
                }).catch(error => {
                    reject(error);
                });
            });
        },
        updateUserInfo: async ({state, dispatch}, data) => {
            const params = {
                id: state.userId,
                ...data,
            };
            const res = await updateUserInfoAPI(params);
            if (res.success) {
                dispatch('getUserInfo');
            }
        },
        updateUserPassword: async ({state, dispatch}, data) => {
            const params = {
                id: state.userId,
                ...data,
            };
            const res = await updateUserInfoAPI(params);
            if (res.success) {
                router.push({path: '/login'});
            }
        },
        updatePersonInfo: async ({state, dispatch}) => {
            const res = await updatePersonInfoAPI(state.currentPersonInfo);
            if (res.success) {
                dispatch('getUserInfoList');
            }
        },
        updateAvatarInfo: async ({state, dispatch}, data) => {
            const params = {
                id: state.userId,
                ...data,
            };
            const res = await updateUserInfoAPI(params);
            if (res.success) {
                dispatch('getUserInfo');
            }
        },
        getUserOrders: async ({state, commit}) => {
            const data = {
                userId: Number(state.userId),
            };
            const res = await getUserOrdersAPI(data);
            if (res.success) {
                commit('set_userOrderList', res.content);
            }
        },
        cancelOrder: async ({state, dispatch}, orderId) => {
            const res = await cancelOrderAPI(orderId);
            if (res.success) {
                dispatch('getUserOrders');
            }
        },
        logout: async ({commit}) => {
            removeToken();
            resetRouter();
            commit('reset_state');
        },
        // remove token
        resetToken({commit}) {
            return new Promise(resolve => {
                removeToken(); // must remove  token  first
                commit('reset_state');
                resolve();
            });
        },
        submitComment: async ({state, dispatch}, data) => {
            const res = await orderCommentAPI(data);
        },
        refreshOrder: async ({state, dispatch}) => {
            location.reload();
        },
        getAvailableOrderList:async ({state,commit})=>{
            const data = {
                userId: Number(state.userId),
            };
            const res = await availableOrdersAPI(data)
            if (res.success){
                commit('set_availableOrderList',res.content)
            }
        }
    },
};

export default user;
