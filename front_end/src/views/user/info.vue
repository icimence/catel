<template>
    <div class="info-wrapper">
        <a-tabs>
            <a-tab-pane key="1" tab="我的信息" style="align-items: center">
                <div style="display: flex;flex-direction: row;justify-content: center;align-items: center">
                    <div class="infoForm">
                        <a-form :form="form">
                            <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }" label="用户名">
                                <a-input
                                        placeholder="请填写用户名"
                                        v-decorator="['username', { rules: [{ required: true, message: '请输入用户名' }] }]"
                                        autocomplete="off"
                                        v-if="modify"
                                />
                                <span v-else>{{ userInfo.username }}</span>
                            </a-form-item>
                            <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }" label="用户头像">
                                <a-upload
                                        :before-upload="beforeUpload"
                                        :show-upload-list="false"
                                        @change="handleChange"
                                        action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
                                        class="avatar-uploader"
                                        list-type="picture-card"
                                        name="avatar"
                                >
                                    <img :src="this.imageUrl" alt="avatar" class="pic-prev" v-if="imageUrl"/>
                                    <div v-else>
                                        <a-icon :type="loading ? 'loading' : 'plus'"/>
                                        <div class="ant-upload-text">
                                            Upload
                                        </div>
                                    </div>
                                </a-upload>
                            </a-form-item>
                            <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" label="邮箱">
                                <span>{{ userInfo.email }}</span>
                            </a-form-item>
                            <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" label="信用值">
                                <span>{{ userInfo.credit }}</span>
                            </a-form-item>
                            <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" label="用户类型">
                                <span v-if="userInfo.userType==='Client' && userInfo.vipType ==='Nil'">普通账号</span>
                                <span style="color: red"
                                      v-if="userInfo.userType==='Client' && userInfo.vipType ==='Small'">年度会员账号</span>
                                <span style="color: #eec710"
                                      v-if="userInfo.userType==='Client' && userInfo.vipType ==='Big'">年度大会员账号</span>
                                <span v-if="userInfo.userType==='HotelManager'">酒店管理员</span>
                                <span v-if="userInfo.userType==='Admin'">系统管理员</span>
                                <span v-if="userInfo.userType==='Marketer'">市场营销人员</span>
                            </a-form-item>
                            <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                                <a-button @click="saveModify" type="primary">
                                    保存
                                </a-button>
                                <a-button @click="cancelModify" style="margin-left: 30px" type="default">
                                    取消
                                </a-button>
                            </a-form-item>
                            <a-form-item :wrapper-col="{ span: 20, offset: 1 }" v-if="!modifyP && !modify">
                                <a-button @click="modifyInfo" type="primary">
                                    修改信息
                                    <a-icon type="edit"/>
                                </a-button>
                                <a-button @click="modifyPassword" style="margin-left: 30px" type="primary">
                                    修改密码
                                    <a-icon type="security-scan"/>
                                </a-button>
                                <a-button @click="upgradeToVIP" style="margin-left: 30px" type="primary"
                                          v-if="this.userInfo.vipType === 'Nil'">
                                    成为VIP
                                    <a-icon type="dollar"/>
                                </a-button>
                                <!--                        <a-button @click="upgradeToVIP" style="margin-left: 30px" type="primary" v-if="this.userInfo.vipType === 'Small'">-->
                                <!--                            升级VIP-->
                                <!--                        </a-button>-->
                            </a-form-item>
                        </a-form>
                    </div>

                    <div class="carousel" v-if="availableOrderList.length === 0">
                        <a-carousel autoplay>
                            <div>
                                <span style="font-size: 20px; color: black;">当前用户没有已预订订单</span>
                            </div>
                        </a-carousel>
                    </div>
                    <div v-else>
                        <h1 style="text-align: center">进行中订单</h1>
                        <div class="carousel" >
                            <a-carousel autoplay>
                                <div :key="index" v-for="(item,index) in availableOrderList">
                                    <a-form class="orderForm">
                                        <a-form-item :label-col="{ span: 7 }" :wrapper-col="{ span: 8, offset: 2  }"
                                                     label="酒店名称" style="font-weight: 600;">
                                            <div style="font-size: 17px; font-weight: 600;">
                                                {{item.hotelName}}
                                            </div>
                                        </a-form-item>
                                        <a-form-item :label-col="{ span: 7 }" :wrapper-col="{ span: 8, offset: 2  }"
                                                     label="房型" style="font-weight: 600;">
                                            <div style="font-size: 17px; font-weight: 600;">
                                                <span v-if="item.roomType === 'BigBed'">大床房</span>
                                                <span v-if="item.roomType === 'DoubleBed'">双床房</span>
                                                <span v-if="item.roomType === 'Family'">家庭房</span>
                                            </div>
                                        </a-form-item>
                                        <a-form-item :label-col="{ span: 7 }" :wrapper-col="{ span: 8, offset: 2  }"
                                                     label="预约时间" style="font-weight: 600;">
                                            <div style="font-size: 17px; font-weight: 600;">
                                                {{item.checkInDate}}
                                            </div>
                                        </a-form-item>
                                    </a-form>
                                </div>
                            </a-carousel>
                        </div>
                    </div>
                </div>
            </a-tab-pane>
            <a-tab-pane key="2" tab="用户信息" v-if="userInfo.userType === 'Client'">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button @click="addUser" type="primary">
                        <a-icon type="plus"/>
                        添加用户
                    </a-button>
                </div>
                <a-table
                        :columns="columns1"
                        :dataSource="userInfoList"
                        bordered
                >
                    <span slot="action" slot-scope="record">
                        <a-button @click.native="showUserInfo(record)" type="default" size="small">查看信息<a-icon
                                type="file-text"/></a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button @click="modifyUserInfo(record)" type="primary" size="small">修改信息<a-icon type="edit"/></a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                                @cancel="cancelDeleteUserInfo"
                                @confirm="confirmDeleteUserInfo(record.id)"
                                cancelText="取消"
                                okText="确定"
                                title="确定想删除该用户信息吗？"
                        >
                            <a-button type="danger" size="small">删除信息<a-icon type="delete"/></a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
                <a-modal
                        :closable="false"
                        :visible="visible_userInfo"
                        title="信息详情"
                >
                    <p style="align-content: center;font-size: 15px">用户姓名: {{modalText_userInfo.realName}}</p>
                    <p style="align-content: center;font-size: 15px">用户手机号: {{modalText_userInfo.phoneNumber}}</p>
                    <p style="align-content: center;font-size: 15px">身份证号码: {{modalText_userInfo.idNo}}</p>
                    <p style="align-content: center;font-size: 15px">用户生日: {{modalText_userInfo.birthday}}</p>
                    <a-button @click.native="closeVisibleUserInfo" slot="footer" type="primary">关闭</a-button>
                </a-modal>
            </a-tab-pane>
            <a-tab-pane key="3" tab="我的订单" v-if="userInfo.userType === 'Client'">
                <a-table
                        :columns="columns"
                        :dataSource="userOrderList"
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
                        <a-button @click.native="showOrder(record)" size="small" type="primary">查看</a-button>
                        <a-divider type="vertical" v-if="record.orderState === 'Available'"></a-divider>
                        <a-popconfirm
                                @cancel="cancelCancelOrder"
                                @confirm="confirmCancelOrder(record.id)"
                                cancelText="取消"
                                okText="确定"
                                title="你确定撤销该笔订单吗？"
                                v-if="record.orderState === 'Available'"
                        >
                            <a-button size="small" type="danger">撤销</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical" v-if="record.orderState === 'Finished'"></a-divider>
                        <a-button @click.native="evaluate(record)" size="small" type="primary"
                                  v-if="record.orderState === 'Finished' && record.commentId == -1">评价</a-button>
                        <a-button @click.native="evaluate(record)" disabled size="small" type="primary"
                                  v-if="record.orderState === 'Finished' && record.commentId != -1">已评价</a-button>
                    </span>
                </a-table>
                <a-modal
                        :closable="false"
                        :visible="visible"
                        title="预定详情"
                >
                    <p style="align-content: center;font-size: 15px">订单编号: {{modalText.id}}</p>
                    <p style="align-content: center;font-size: 15px">酒店名称: {{modalText.hotelName}}</p>
                    <p style="align-content: center;font-size: 15px" v-if="modalText.roomType=='BigBed'">房型:大床房</p>
                    <p style="align-content: center;font-size: 15px" v-if="modalText.roomType=='DoubleBed'">
                        房型:双床房</p>
                    <p style="align-content: center;font-size: 15px" v-if="modalText.roomType=='Family'">房型:家庭房</p>
                    <p style="align-content: center;font-size: 15px">入住时间: {{modalText.checkInDate}}</p>
                    <p style="align-content: center;font-size: 15px">离店时间: {{modalText.checkOutDate}}</p>
                    <p style="align-content: center;font-size: 15px">人数: {{modalText.peopleNum}}</p>
                    <p style="align-content: center;font-size: 15px">房间数: {{modalText.roomNum}}</p>
                    <p style="align-content: center;font-size: 15px">房价: {{modalText.price}}</p>
                    <p style="align-content: center;font-size: 15px" v-if="modalText.orderState=='Available'">
                        订单状态:已预订</p>
                    <a-button @click.native="closeVisible" slot="footer" type="primary">关闭</a-button>
                </a-modal>
            </a-tab-pane>
            <a-tab-pane key="4" tab="信用值变更记录" v-if="userInfo.userType === 'Client'">
                <a-table
                        :columns="columns2"
                        :dataSource="creditTrans"
                        bordered
                >
                    <span slot="id" slot-scope="text">
                        <span>{{text}}</span>
                    </span>
                    <span slot="hotelName" slot-scope="text">
                        <span>{{text}}</span>
                    </span>
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text === 'BigBed'">大床房</span>
                        <span v-if="text === 'DoubleBed'">双床房</span>
                        <span v-if="text === 'Family'">家庭房</span>
                    </span>
                    <span slot="checkInDate" slot-scope="text">
                        <span>{{text}}</span>
                    </span>
                    <span slot="checkOutDate" slot-scope="text">
                        <span>{{text}}</span>
                    </span>
                    <span slot="peopleNum" slot-scope="text">
                        <span>{{text}}</span>
                    </span>
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="orderState" slot-scope="text">
                        <a-tag color="green" v-if="text === 'Available'">已预定</a-tag>
                        <a-tag color="green" v-if="text === 'Direct'">信用值充值</a-tag>
                        <a-tag color="blue" v-if="text === 'Finished'">已入住</a-tag>
                        <a-tag color="red" v-if="text === 'Canceled'">已撤销</a-tag>
                        <a-tag color="red" v-if="text === 'Expired'">有异常</a-tag>
                    </span>
                    <span slot="creditDelta" slot-scope="text">
                        <span>{{ text }}</span>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <ModifyUserInfo></ModifyUserInfo>
        <ModifyPassword></ModifyPassword>
        <AddUserInfo></AddUserInfo>
        <UpgradeToVIP></UpgradeToVIP>
        <EvaluateOrders :commentContext="commentContext" :record="this.record_parentToChild"
                        :thank="this.thank"></EvaluateOrders>
    </div>
</template>
<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';
    import ModifyUserInfo from './component/modifyUserInfo';
    import AddUserInfo from './component/addUserInfo';
    import UpgradeToVIP from './component/upgradeToVIP';
    import EvaluateOrders from './component/evaluateOrders';
    import ModifyPassword from './component/modifyPassword';

    function getBase64(img, callback) {
        const reader = new FileReader();
        reader.addEventListener('load', () => callback(reader.result));
        reader.readAsDataURL(img);
    }

    const columns = [
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
            title: '房价',
            dataIndex: 'price',
        },
        {
            title: '状态',
            filters: [{text: '已预订', value: 'Available'}, {text: '已撤销', value: 'Canceled'}, {
                text: '已入住',
                value: 'Finished'
            }, {text: '有异常', value: 'Expired'}],
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
    const columns1 = [
        {
            title: '用户姓名',
            dataIndex: 'realName',
            width: '15%'
        },
        {
            title: '用户手机号',
            dataIndex: 'phoneNumber',
            width: '20%'
        },
        {
            title: '身份证号码',
            dataIndex: 'idNo',
            width: '20%'
        },
        {
            title: '用户生日',
            dataIndex: 'birthday',
            width: '15%'
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    const columns2 = [
        {
            title: '信用值变更',
            dataIndex: 'creditDelta',
        },
        {
            title: '订单号',
            dataIndex: 'id',
        },
        {
            title: '酒店名',
            dataIndex: 'hotelName',
        },
        {
            title: '状态',
            filters: [{text: '信用值充值', value: 'Direct'}, {text: '已撤销', value: 'Canceled'}, {
                text: '已入住',
                value: 'Finished',
            }],
            onFilter: (value, record) => record.orderState.includes(value),
            dataIndex: 'orderState',
            scopedSlots: {customRender: 'orderState'},
        },

    ];
    export default {
        name: 'info',
        data() {
            return {
                modify: false,
                modifyP: false,
                formLayout: 'horizontal',
                pagination: {},
                columns,
                columns1,
                columns2,
                loading: false,
                imageUrl: '',
                data: [],
                form: this.$form.createForm(this, {name: 'coordinated'}),
                modalText: {},
                modalText_userInfo: {},
                visible: false,
                visible_userInfo: false,
                record_parentToChild: {},
                thank: false,
                commentContext: '',
                userRunningList: []
            };
        },
        components: {
            ModifyUserInfo,
            AddUserInfo,
            UpgradeToVIP,
            EvaluateOrders,
            ModifyPassword,
        },
        computed: {
            ...mapGetters([
                'userId',
                'userInfo',
                'userOrderList',
                'orderDetailVisible',
                'userInfoList',
                'modifyUserInfoListVisible',
                'evaluateOrdersVisible',
                'modifyPasswordVisible',
                'creditTrans',
                "availableOrderList"
            ]),
        },
        created() {

        },
        async mounted() {
            await this.getUserInfo();
            await this.getUserOrders();
            await this.getUserInfoList();
            await this.getCreditTrans();
            await this.getAvailableOrderList();
            this.imageUrl = this.userInfo.avatar;
            this.userRunningList = this.userOrderList()
        },
        methods: {
            ...mapMutations([
                'set_orderDetailVisible',
                'set_addUserInfoVisible',
                'set_modifyUserInfoListVisible',
                'set_currentPerson',
                'set_currentPersonId',
                'set_upgradeToVIPVisible',
                'set_evaluateOrdersVisible',
                'set_modifyPasswordVisible',
            ]),
            ...mapActions([
                'getCreditTrans',
                'getUserInfo',
                'getUserOrders',
                'updateUserInfo',
                'cancelOrder',
                'updateAvatarInfo',
                'getUserInfoList',
                'deleteUserInfoList',
                'getPersonById',
                'getAvailableOrderList'
            ]),
            showOrder(record) {
                //this.set_orderDetailVisible(true)
                console.log('查看该订单的record');
                console.log(record);
                this.visible = true;
                this.modalText = record;
            },
            closeVisible() {
                //this.set_orderDetailVisible(false)
                this.visible = false;
            },
            closeVisibleUserInfo() {
                this.visible_userInfo = false;
            },
            upgradeToVIP() {
                this.set_upgradeToVIPVisible(true);
            },
            handleChange(info) {
                if (info.file.status === 'uploading') {
                    this.loading = true;
                    return;
                }
                if (info.file.status === 'done') {

                    // Get this url from response in real world.
                    getBase64(info.file.originFileObj, imageUrl => {
                        // this.imageUrl = imageUrl;
                        this.loading = false;
                        const data = {
                            avatar: imageUrl.substring(imageUrl.indexOf(',') + 1),
                        };
                        this.imageUrl = imageUrl;
                        this.updateAvatarInfo(data);
                        // location.reload()
                        // this.$router.go(0)
                    });
                }
            },
            beforeUpload(file) {
                const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
                if (!isJpgOrPng) {
                    this.$message.error('You can only upload IMG file!');
                }
                const isLt2M = file.size / 1024 / 1024 < 2;
                if (!isLt2M) {
                    this.$message.error('Image must smaller than 2MB!');
                }
                return isJpgOrPng && isLt2M;
            },
            saveModify() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            username: this.form.getFieldValue('username'),
                        };
                        this.updateUserInfo(data).then(() => {
                            this.modify = false;
                        });
                    }
                });
            },
            modifyInfo() {
                setTimeout(() => {
                    this.form.setFieldsValue({
                        'username': this.userInfo.username,
                    });
                }, 0);
                this.modify = true;
            },
            modifyPassword() {
                this.set_modifyPasswordVisible(true);
            },
            cancelModify() {
                this.modify = false;
            },
            confirmCancelOrder(orderId) {
                this.cancelOrder(orderId);
            },
            cancelCancelOrder() {

            },
            addUser() {
                this.set_addUserInfoVisible(true);
            },
            modifyUserInfo(record) {
                this.getPersonById(record.id);
                this.set_modifyUserInfoListVisible(true);
            },
            showUserInfo(record) {
                this.visible_userInfo = true;
                this.modalText_userInfo = record;
            },
            confirmDeleteUserInfo(id) {
                this.deleteUserInfoList(id);
            },
            cancelDeleteUserInfo() {

            },
            evaluate(record) {
                this.record_parentToChild = record;
                console.log('点击反馈');
                this.set_evaluateOrdersVisible(true);
                console.log('evaluateOrdersVisible: ' + this.evaluateOrdersVisible);
                this.thank = false;
                this.commentContext = '';
            },
        },
    };
</script>
<style lang="less" scoped>
    .info-wrapper {
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
    .info-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style>

    .orderForm {
        margin-top: 8%;
        margin-left: 28%;
        transform: scale(1.2);
        text-align: left;
    }

    .infoForm {
        margin-top: 30px;
        width: 50%;
    }

    .carousel {
        width: 560px;
        display: block;
        border-style: double;
        border-width: 10px;
        border-color: white;
    }

    .avatar-uploader > .ant-upload {
        width: 128px;
        height: 128px;
    }

    .pic-prev {
        width: 128px;
        height: 128px;
    }

    .ant-upload-select-picture-card i {
        font-size: 32px;
        color: #999;
    }

    .ant-upload-select-picture-card .ant-upload-text {
        margin-top: 8px;
        color: #666;
    }
</style>
<style scoped>
    .ant-carousel >>> .slick-slide {
        text-align: center;
        height: 260px;
        line-height: 260px;
        background-image: linear-gradient(to top, #a8edea 0%, #fed6e3 100%);
        overflow: hidden;
    }

    .ant-carousel >>> .slick-slide h3 {
        color: #fff;
    }
</style>
