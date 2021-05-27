<template>
    <div class="manageUser-wrapper">
        <a-tabs>
            <a-tab-pane key="1" tab="酒店管理员管理">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button @click="addManager" type="primary">
                        <a-icon type="plus"/>
                        添加酒店管理员
                    </a-button>
                </div>
                <a-table
                        :columns="columns"
                        :dataSource="managerList"
                        bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="action" slot-scope="text, record">
                        <a-button @click="addHotelToManager(record)" type="primary">绑定酒店<a-icon type="key" /></a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                                @confirm="deleteThisUser(record)"
                                cancelText="取消"
                                okText="确定"
                                title="确定想删除该用户吗？此操作将不可逆！"
                        >
                            <a-button type="danger">删除用户<a-icon type="delete" /></a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane key="2" tab="营销人员管理">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button @click="addMarketer" type="primary">
                        <a-icon type="plus"/>
                        添加营销账号
                    </a-button>
                </div>
                <a-table
                        :columns="columns"
                        :dataSource="marketerList"
                        bordered
                >
                    <span slot="action" slot-scope="text, record">
                        <a-popconfirm
                                @confirm="deleteThisUser(record)"
                                cancelText="取消"
                                okText="确定"
                                title="确定想删除该用户吗？此操作将不可逆！"
                        >
                            <a-button type="danger">删除用户<a-icon type="delete" /></a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane key="3" tab="酒店管理">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button @click="addHotel" type="primary">
                        <a-icon type="plus"/>
                        添加酒店
                    </a-button>
                </div>
                <a-table
                        :columns="columns1"
                        :dataSource="hotelList"
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
                        <a-popconfirm
                                @confirm="deleteThisHotel(record)"
                                cancelText="取消"
                                okText="确定"
                                title="确定想删除该酒店吗？"
                        >
                            <a-button type="danger">删除酒店<a-icon type="delete" /></a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddHotelToManager></AddHotelToManager>
        <AddHotelModal></AddHotelModal>
        <AddManagerModal></AddManagerModal>
        <AddMarketerModal></AddMarketerModal>
    </div>
</template>
<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';
    import AddManagerModal from './components/addManagerModal';
    import AddHotelModal from './components/addHotelModal';
    import AddHotelToManager from './components/addHotelToManager';
    import AddMarketerModal from "./components/addMarketerModal";

    const columns = [
        {
            title: '用户邮箱',
            dataIndex: 'email',
            width: '25%',
        },
        {
            title: '用户名',
            dataIndex: 'username',
            width: '20%',
        },
        {
            title: '用户密码',
            dataIndex: 'password',
            width: '20%',
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    const columns1 = [
        {
            title: '酒店名',
            dataIndex: 'name',
        },
        {
            title: '商圈',
            dataIndex: 'bizRegion',
        },
        {
            title: '地址',
            dataIndex: 'address',
        },
        {
            title: '酒店星级',
            dataIndex: 'hotelStar',
            scopedSlots: {customRender: 'hotelStar'},
        },
        {
            title: '评分',
            dataIndex: 'rate',
        },
        {
            title: '简介',
            dataIndex: 'description',
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
                columns,
                columns1,
                data: [],
                form: this.$form.createForm(this, {name: 'manageUser'}),
            };
        },
        components: {
            AddManagerModal,
            AddHotelModal,
            AddHotelToManager,
            AddMarketerModal,
        },
        computed: {
            ...mapGetters([
                'hotelList',
                'addHotelModalVisible',
                'addManagerModalVisible',
                'addHotelToManagerVisible',
                'managerList',
                "marketerList"
            ]),
        },
        mounted() {
            this.getManagerList();
            this.getHotelList();
            this.getMarketerList();
        },
        methods: {
            ...mapActions([
                'getHotelList',
                'getManagerList',
                'getHotelListByManager',
                'deleteUser',
                'deleteHotel',
                'getUnboundHotelList',
                "getMarketerList"
            ]),
            ...mapMutations([
                'set_addHotelModalVisible',
                'set_addManagerModalVisible',
                'set_addHotelToManagerVisible',
                'set_addMarketerModalVisible',
                'set_hotelManagerId',
            ]),
            addManager() {
                this.set_addManagerModalVisible(true);
            },
            addMarketer(){
                this.set_addMarketerModalVisible(true);
            },
            addHotel() {
                this.set_addHotelModalVisible(true);
            },
            addHotelToManager(record) {
                this.set_hotelManagerId(record.id);
                this.getHotelListByManager(record.id);
                this.getUnboundHotelList();
                this.set_addHotelToManagerVisible(true);
            },
            deleteThisHotel(record) {
                this.deleteHotel(record.id);
            },
            deleteThisUser(record) {
                this.deleteUser(record.id);
            },
        },
    };
</script>
<style lang="less" scoped>
    .manageUser-wrapper {
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
    .manageUser-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>


