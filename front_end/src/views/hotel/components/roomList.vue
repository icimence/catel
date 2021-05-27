<template>
    <div class="room-list">
        <div class="filter">

        </div>
        <div class="list">
            <a-table
                    :columns="columns"
                    :dataSource="rooms"
            >
                <span slot="roomType" slot-scope="text">
                    <span v-if="text === 'BigBed'">大床房</span>
                    <span v-if="text === 'DoubleBed'">双床房</span>
                    <span v-if="text === 'Family'">家庭房</span>
                </span>
                <span slot="breakfast" slot-scope="text">
                    <span v-if="text === true">有</span>
                    <span v-if="text === false">无</span>
                </span>
                <span slot="peopleMax" slot-scope="text">
                    <span>{{ text }}</span>
                </span>
                <span slot="curNum" slot-scope="text">
                    <span>{{ text }}</span>
                </span>
                <span slot="price" slot-scope="text">
                    <span>￥{{ text }}</span>
                </span>
                <span slot="action" slot-scope="text, record">
                    <a-button @click="order(record)" type="primary" v-if="userInfo.userType==='Client'">预定<a-icon
                            type="bell"/></a-button>
                    <a-button @click="order(record)" type="primary" v-else disabled="on">预定<a-icon
                            type="bell"/></a-button>
                </span>
            </a-table>
        </div>
        <OrderModal></OrderModal>
    </div>
</template>
<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';
    import OrderModal from './orderModal';

    const columns = [
        {
            title: '房型',
            dataIndex: 'roomType',
            key: 'roomType',
            scopedSlots: {customRender: 'roomType'},
            width: '22%'
        },
        {
            title: '早餐',
            dataIndex: 'breakfast',
            key: 'breakfast',
            scopedSlots: {customRender: 'breakfast'},
            width: '22%'
        },
        {
            title: '入住人数',
            key: 'peopleMax',
            dataIndex: 'peopleMax',
            scopedSlots: {customRender: 'peopleMax'},
            width: '22%'
        },
        {
            title: '房价',
            key: 'price',
            dataIndex: 'price',
            scopedSlots: {customRender: 'price'},
            width: '22%'
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    export default {
        name: 'roomList',
        props: {
            rooms: {
                type: Array,
            },
        },
        data() {
            return {
                columns,
            };
        },
        components: {
            OrderModal,
        },
        computed: {
            ...mapGetters([
                'orderModalVisible',
                'userId',
                'userInfo',
                "currentOrderRoom"
            ]),
        },
        monuted() {

        },
        methods: {
            ...mapMutations([
                'set_orderModalVisible',
                'set_currentOrderRoom',
            ]),
            ...mapActions([
                'getPersonList',
                "getAvailableRoom"
            ]),
            order(record) {
                this.set_currentOrderRoom(record);
                this.set_orderModalVisible(true);
                this.getAvailableRoom(this.currentOrderRoom.id)
                this.getPersonList(this.userId);
                console.log('roomId')
                console.log(this.currentOrderRoom.id);
            },
        },
    };
</script>
