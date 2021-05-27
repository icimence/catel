import {addHotelAPI, addRoomAPI, getHotelByManagerAPI, getRoomListAPI, deleteRoomAPI} from '@/api/hotelManager';
import {cancelOrderAPI, getAllOrdersAPI, getOrderByManagerAPI, errorOrderAPI} from '@/api/order';
import {addCouponAPI, hotelAllCouponsAPI, deleteCouponAPI} from '@/api/coupon';

const hotelManager = {
    state: {
        hotelListMatchManager: [],
        orderListMatchManager: [],
        ManagerId: '',
        orderList: [],
        addHotelParams: {
            name: '',
            address: '',
            bizRegion: 'XiDan',
            hotelStar: '',
            rate: 0,
            description: '',
            phoneNumber: '',
            managerId: '',
        },
        addHotelModalVisible: false,
        addRoomParams: {
            roomType: '',
            hotelId: '',
            price: '',
            total: 0,
            curNum: 0,
            breakfast: '',
            peopleMax: '',
        },
        allRoomModalVisible: false,
        addRoomModalVisible: false,
        couponVisible: false,
        addCouponVisible: false,
        activeHotelId: 0,
        couponList: [],
        roomList: [],
        managerOrderDetailVisible: false,
    },
    mutations: {
        set_roomList: function (state, data) {
            state.roomList = data;
        },
        set_allRoomModalVisible: function (state, data) {
            state.allRoomModalVisible = data;
        },
        set_managerId: function (state, data) {
            state.ManagerId = data;
        },
        set_hotelListMatchManager: function (state, data) {
            state.hotelListMatchManager = data;
        },
        set_orderListMatchManager: function (state, data) {
            state.orderListMatchManager = data;
        },
        set_orderList: function (state, data) {
            state.orderList = data;
        },
        set_addHotelModalVisible: function (state, data) {
            state.addHotelModalVisible = data;
        },
        set_addHotelParams: function (state, data) {
            state.addHotelParams = {
                ...state.addHotelParams,
                ...data,
            };
        },
        set_addRoomModalVisible: function (state, data) {
            state.addRoomModalVisible = data;
        },
        set_addRoomParams: function (state, data) {
            state.addRoomParams = {
                ...state.addRoomParams,
                ...data,
            };
        },
        set_couponVisible: function (state, data) {
            state.couponVisible = data;
        },
        set_activeHotelId: function (state, data) {
            state.activeHotelId = data;
        },
        set_couponList: function (state, data) {
            state.couponList = data;
        },
        set_addCouponVisible: function (state, data) {
            state.addCouponVisible = data;
        },
        set_managerOrderDetailVisible: function (state, data) {
            state.managerOrderDetailVisible = data;
        },
    },
    actions: {
        deleteRoom: async ({dispatch, state}, data) => {
            const res = await deleteRoomAPI(data);
            if (res.success){
                dispatch('getRoomList',state.activeHotelId)
            }
        },
        getRoomList: async ({commit, state}, data) => {
            commit('set_activeHotelId',data);
            const res = await getRoomListAPI(data);
            if (res.success) {
                commit('set_roomList', res.content);
            }
        },
        getHotelListByManager: async ({commit, state}, data) => {
            const res = await getHotelByManagerAPI({
                id: Number(data),
            });
            if (res.success) {
                commit('set_hotelListMatchManager', res.content);
            }
        },
        getOrderListByManager: async ({commit, state}, data) => {
            const res = await getOrderByManagerAPI({
                id: Number(data),
            });
            if (res.success) {
                commit('set_orderListMatchManager', res.content);
            }
        },
        getAllOrders: async ({state, commit}) => {
            const res = await getAllOrdersAPI();
            if (res.success) {
                commit('set_orderList', res.content);
            }
        },
        addHotel: async ({state, dispatch, commit}) => {
            const res = await addHotelAPI(state.addHotelParams);
            if (res.success) {
                dispatch('getHotelList');
                commit('set_addHotelParams', {
                    name: '',
                    address: '',
                    bizRegion: 'XiDan',
                    hotelStar: '',
                    rate: 0,
                    description: '',
                    phoneNumber: '',
                    managerId: '',
                });
                commit('set_addHotelModalVisible', false);
            }
        },
        addRoom: async ({state, commit,dispatch}) => {
            const res = await addRoomAPI(state.addRoomParams);
            if (res.success) {
                commit('set_addRoomModalVisible', false);
                commit('set_addRoomParams', {
                    roomType: '',
                    hotelId: '',
                    price: '',
                    total: 0,
                    curNum: 0,
                    breakfast: '',
                    peopleMax: '',
                });
                commit('set_allRoomModalVisible',true);
                dispatch('getRoomList',state.activeHotelId)
            }
        },
        getHotelCoupon: async ({state, commit}) => {
            const res = await hotelAllCouponsAPI(state.activeHotelId);
            if (res.success) {
                // 获取到酒店策略之后的操作（将获取到的数组赋值给couponList）
                commit('set_couponList', res.content);
            }
        },
        addHotelCoupon: async ({commit, dispatch}, data) => {
            const res = await addCouponAPI(data);
            if (res.success) {
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                dispatch('getHotelCoupon');
                commit('set_addCouponVisible', false);
                commit('set_couponVisible', true);
            }
        },
        deleteHotelCoupon: async ({commit, dispatch}, data) => {
            const res = await deleteCouponAPI(data);
            if (res.success) {
                dispatch('getHotelCoupon');
            }
        },
        getManagerOrders: async ({state, commit}) => {
            const res = await getOrderByManagerAPI({
                id: Number(state.hotelListMatchManager[0].managerId),
            });
            if (res.success) {
                commit('set_orderListMatchManager', res.content);
            }
        },
        errorOrder: async ({state, dispatch}, orderId) => {
            const res = await errorOrderAPI(orderId);
            if (res.success) {
                dispatch('getManagerOrders');
            }
        },
        managerDeleteOrder: async ({state, dispatch}, orderId) => {
            const res = await cancelOrderAPI(orderId);
            if (res.success) {
                dispatch('getManagerOrders');
                console.log(state);
                console.log(state.hotelListMatchManager[0].managerId);
            }
        },
    },
};
export default hotelManager;
