<template>
    <div class="manageHotel-wrapper">
        <a-tabs>
            <a-tab-pane key="1" tab="酒店管理">
                <!--                <div style="width: 100%; text-align: right; margin:20px 0">-->
                <!--                    <a-button type="primary" @click="addHotel"><a-icon type="plus" />添加酒店</a-button>-->
                <!--                </div>-->
                <div class="manageTable">
                <a-table
                    :columns="columns1"
                    :dataSource="hotelListMatchManager"
                    bordered
                >
                    <span slot="hotelStar" slot-scope="hotelStar">
                        <span v-if="hotelStar === 'One'">
                            一星级酒店
                        </span>
                        <span v-if="hotelStar === 'Two'">
                            二星级酒店
                        </span>
                        <span v-if="hotelStar === 'Three'">
                            三星级酒店
                        </span>
                        <span v-if="hotelStar === 'Four'">
                            四星级酒店
                        </span>
                        <span v-if="hotelStar === 'Five'">
                            五星级酒店
                        </span>
                    </span>
                    <span slot="action" slot-scope="record">
                        <a-button @click="addRoom(record)" size="small" type="primary">管理房型<a-icon type="key" /></a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button @click="showCoupon(record)" size="small" type="info">优惠策略<a-icon type="shopping-cart" /></a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button @click="showForm(record)" size="small" type="info">修改信息<a-icon type="edit" /></a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button @click="showAnnouncement(record)" size="small" type="info">促销公告<a-icon type="file-text" /></a-button>
                    </span>
                </a-table>
                </div>
            </a-tab-pane>
            <a-tab-pane key="2" tab="订单管理">
                <a-table
                    :columns="columns2"
                    :dataSource="orderListMatchManager"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text === 'BigBed'">大床房</span>
                        <span v-if="text === 'DoubleBed'">双床房</span>
                        <span v-if="text === 'Family'">家庭房</span>
                    </span>
                    <span slot="orderState" slot-scope="text">
                        <a-tag color="green" v-if="text === 'Available'">已预定</a-tag>
                        <a-tag color="blue" v-if="text === 'Finished'">已入住</a-tag>
                        <a-tag color="red" v-if="text === 'Canceled'">已撤销</a-tag>
                        <a-tag color="red" v-if="text === 'Expired'">有异常</a-tag>
                    </span>
                    <span slot="action" slot-scope="record">
                        <a-button @click.native="showOrderDetail(record)" size="small" type="primary">订单详情 <a-icon type="file-text" /></a-button>
                        <a-divider type="vertical" v-if="record.orderState === 'Available' || record.orderState === 'Finished'"></a-divider>
                        <a-popconfirm
                                @cancel="cancelSetError"
                                @confirm="setError(record.id)"
                                cancelText="取消"
                                okText="确定"
                                title="确定将订单更改为异常状态吗？"
                                v-if="record.orderState === 'Finished'"
                        >
                            <a-button size="small" type="danger" >设置异常<a-icon type="bug" /></a-button>
                        </a-popconfirm>
                        <a-popconfirm
                            @cancel="cancelCancelOrder"
                            @confirm="deleteOrder(record.id)"
                            cancelText="取消"
                            okText="确定"
                            title="确定想删除该订单吗？"
                            v-if="record.orderState === 'Available'"
                        >
                            <a-button type="danger" size="small">删除订单<a-icon type="delete" /></a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-modal
                :closable="false"
                :visible="visible"
                @close="onClose"
                title="订单详情"
            >
                <p style="align-content: center;font-size: 15px">订单编号: {{modalText.id}}</p>
                <p style="align-content: center;font-size: 15px">酒店名称: {{modalText.hotelName}}</p>
                <p style="align-content: center;font-size: 15px" v-if="modalText.roomType=='BigBed'">房型:大床房</p>
                <p style="align-content: center;font-size: 15px" v-if="modalText.roomType=='DoubleBed'">房型:双床房</p>
                <p style="align-content: center;font-size: 15px" v-if="modalText.roomType=='Family'">房型:家庭房</p>
                <p style="align-content: center;font-size: 15px">入住时间: {{modalText.checkInDate}}</p>
                <p style="align-content: center;font-size: 15px">离店时间: {{modalText.checkOutDate}}</p>
                <p style="align-content: center;font-size: 15px">人数: {{modalText.peopleNum}}</p>
                <p style="align-content: center;font-size: 15px">房价: {{modalText.price}}</p>
                <p style="align-content: center;font-size: 15px" v-if="modalText.orderState=='Available'">订单状态:已预订</p>
                <a-button @click.native="closeVisible" slot="footer" type="primary">关闭</a-button>
            </a-modal>
        </a-tabs>
        <AllRoomModal></AllRoomModal>
        <Coupon></Coupon>
        <ModifyHotelInfo></ModifyHotelInfo>
        <ModifyAnnouncement></ModifyAnnouncement>
    </div>
</template>
<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';
    import AllRoomModal from './components/allRoomModal';
    import Coupon from './components/coupon';
    import ModifyHotelInfo from './components/modifyHotelInfo';
    import ModifyAnnouncement from './components/modifyAnnouncement';

    const moment = require('moment');
    const columns1 = [
        {
            title: '酒店名',
            dataIndex: 'name',
            width: '8%'
        },
        {
            title: '商圈',
            dataIndex: 'bizRegion',
            width: '6%'
        },
        {
            title: '地址',
            dataIndex: 'address',
            width: '8%'
        },
        {
            title: '酒店星级',
            dataIndex: 'hotelStar',
            scopedSlots: {customRender: 'hotelStar'},
            width: '12%'
        },
        {
            title: '评分',
            dataIndex: 'rate',
            width: '4%'
        },
        {
            title: '简介',
            dataIndex: 'description',
            width: '24%'
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    const columns2 = [
        {
            title: '订单号',
            dataIndex: 'id',
        },
        {
            title: '酒店名',
            dataIndex: 'hotelName',
        },
        {
            title: '房型',
            dataIndex: 'roomType',
            scopedSlots: {customRender: 'roomType'},
        },
        {
            title: '入住时间',
            dataIndex: 'checkInDate',
            scopedSlots: {customRender: 'checkInDate'},
        },
        {
            title: '离店时间',
            dataIndex: 'checkOutDate',
            scopedSlots: {customRender: 'checkOutDate'},
        },
        {
            title: '入住人数',
            dataIndex: 'peopleNum',
        },
        {
            title: '房价',
            dataIndex: 'price',
        },
        {
            title: '状态',
            filters: [{text: '已预订', value: 'Available'}, {text: '已撤销', value: 'Canceled'}, {text: '已入住', value: 'Finished'}, {text: '有异常', value: 'Expired'}],
            onFilter: (value, record) => record.orderState.includes(value),
            dataIndex: 'orderState',
            scopedSlots: {customRender: 'orderState'},
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    export default {
        name: 'manageHotel',
        data() {
            return {
                formLayout: 'horizontal',
                pagination: {},
                columns1,
                columns2,
                form: this.$form.createForm(this, {name: 'manageHotel'}),
                managerId: 0,
                visible: false,
                modalText: {},
                formVisible: false,
                modify: false,
            };
        },
        components: {
            ModifyAnnouncement,
            AllRoomModal,
            Coupon,
            ModifyHotelInfo,
        },
        computed: {
            ...mapGetters([
                'orderListMatchManager',
                'hotelListMatchManager',
                'addHotelModalVisible',
                'activeHotelId',
                'couponVisible',
                'userId',
                'managerOrderDetailVisible',
                'manageHotelVisible',
                'announcementVisible',
                "allRoomModalVisible"
            ]),
        },

        created() {
            console.log('显示orderListMatchManager信息');
            console.log(this.orderListMatchManager);
        },

        async mounted() {
            var that = this;
            await this.getUserInfo();
            let b = this.getUserId();
            var a = Promise.resolve(b);
            await a.then(function (result) {
                that.managerId = result;
            });
            console.log(this.managerId);
            await this.getHotelListByManager(this.managerId);
            await this.getOrderListByManager(this.managerId);
            await this.getAllOrders();
        },
        methods: {
            ...mapMutations([
                'set_addHotelModalVisible',
                'set_allRoomModalVisible',
                'set_couponVisible',
                'set_activeHotelId',
                'set_managerId',
                'set_managerOrderDetailVisible',
                'set_hotelInfoParams',
                'set_manageHotelVisible',
                'set_current',
                'set_currentHotelId',
                'set_announcementVisible',
            ]),
            ...mapActions([
                'getHotelList',
                'getAllOrders',
                'getHotelCoupon',
                'getHotelListByManager',
                'getOrderListByManager',
                'getUserInfo',
                'getUserId',
                'managerDeleteOrder',
                'updateHotelInfo',
                'getHotelById',
                'errorOrder',
                "getRoomList"
            ]),
            addHotel() {
                this.set_addHotelModalVisible(true);
            },
            addRoom(record) {
                this.set_activeHotelId(record.id);
                this.set_allRoomModalVisible(true);
                this.getRoomList(record.id);
                console.log(this.allRoomModalVisible);
            },
            showCoupon(record) {
                this.set_activeHotelId(record.id);
                this.set_couponVisible(true);
                this.getHotelCoupon();
            },
            deleteHotel() {

            },
            showOrderDetail(record) {
                console.log('检查record的值');
                console.log(record);
                this.visible = true;
                this.modalText = record;
            },
            closeVisible() {
                this.visible = false;
            },
            onClose() {
                this.visible = false;
            },
            deleteOrder(orderId) {
                this.managerDeleteOrder(orderId);
            },
            cancelCancelOrder() {

            },
            showForm(record) {
                this.set_current(record);
                this.set_currentHotelId(record.id);
                this.getHotelById();
                this.set_manageHotelVisible(true);
            },
            showAnnouncement(record) {
                this.set_currentHotelId(record.id);
                this.getHotelById();
                this.set_announcementVisible(true);
                console.log(this.announcementVisible);
            },
            setError(orderId) {
                this.errorOrder(orderId)
            },
            cancelSetError() {

            }
        },
    };
</script>
<style lang="less" scoped>
    .manageHotel-wrapper {
        padding: 50px;

        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .manageHotel-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>
