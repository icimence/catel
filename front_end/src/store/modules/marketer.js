import {getAllPlatformCouponAPI, deleteCouponAPI, addCouponAPI} from "@/api/coupon";
import {creditUpAPI} from "@/api/user";

const marketer = {
    state: {
        allPlatformCouponList: [],
        addPlatformCouponModalVisible: false,
    },
    mutations: {
        set_addPlatformCouponModalVisible: function (state, data) {
            state.addPlatformCouponModalVisible = data;
        },
        set_allPlatformCouponList: function (state, data) {
            state.allPlatformCouponList = data;
        },
    },
    actions: {
        getAllPlatformCouponList: async ({state, commit}) => {
            const res = await getAllPlatformCouponAPI();
            if (res.success) {
                commit('set_allPlatformCouponList', res.content);
            }
        },
        addPlatformCoupon: async ({state, commit, dispatch}, data) => {
            const res = await addCouponAPI(data);
            if (res.success) {
                dispatch('getAllPlatformCouponList');
                commit('set_addPlatformCouponModalVisible', false);
            }
        },
        deletePlatformCoupon: async ({state, dispatch}, data) => {
            const res = await deleteCouponAPI(data);
            if (res.success) {
                dispatch('getAllPlatformCouponList');
            }
        },
        creditUP:async ({state},data)=>{
            const res = await creditUpAPI(data);
        }
    }
};
export default marketer;
