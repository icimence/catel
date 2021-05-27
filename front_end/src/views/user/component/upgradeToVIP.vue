<template>
    <a-modal
        :visible="upgradeToVIPVisible"
        @cancel="cancel"
        @ok="cancel"
        cancelText="取消"
        okText="我再想想"
        title="添加用户信息"
    >
        <a-form>
            <a-form-item>
                <div class="focusCenter" v-if="this.userInfo.vipType === 'Nil'">
                    <p>升级为年度会员，将会直接从账户中扣除300元</p>
                    <a-button @click="showSmallVIPConfirm" type="primary">
                        <a-icon type="alipay" />
                        确定
                    </a-button>
                </div>
            </a-form-item>
            <a-form-item>
                <div class="focusCenter" v-if="this.userInfo.vipType === 'Nil'">
                    <p>升级为年度大会员，将会直接从账户中扣除500元</p>
                    <a-button @click="showBigVIPConfirm" type="primary">
                        <a-icon type="alipay" />
                        确定
                    </a-button>
                </div>
            </a-form-item>
            <a-form-item>
                <div class="focusCenter" v-if="this.userInfo.vipType === 'Small'">
                    <p>升级为年度大会员，将会直接从账户中扣除200元作为升级费用</p>
                    <a-button @click="showBigVIPConfirm" type="primary">
                        <a-icon type="alipay" />
                        确定
                    </a-button>
                </div>
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';

    export default {
        name: 'upgradeToVIP',
        data() {
            return {
                form: this.$form.createForm(this, {name: 'upgradeToVIP'}),
            };
        },
        computed: {
            ...mapGetters([
                'upgradeToVIPVisible',
                'userInfo',
                'userId',
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'upgradeToVIP'});
        },
        mounted() {
        },
        methods: {
            ...mapMutations([
                'set_upgradeToVIPVisible',
            ]),
            ...mapActions([
                'upgradeToVIP',
            ]),
            cancel() {
                this.set_upgradeToVIPVisible(false);
            },
            upgradeToSmallVIP() {
                const data = {
                    userId: this.userId,
                    day: 365,
                    vipType: 'Small',
                };
                this.upgradeToVIP(data);
                this.openNotification();
            },
            upgradeToBigVIP() {
                const data = {
                    userId: this.userId,
                    day: 365,
                    vipType: 'Big',
                };
                this.upgradeToVIP(data);
                this.openNotification();
            },
            openNotification() {
                this.$notification.open({
                    message: '充值成功提醒',
                    description:
                        '您刚刚的充值已经生效，请刷新页面内获取最新的用户信息',
                    icon: <a-icon type="smile" style="color: #108ee9" />,
                    duration: 2,
                });
            },
            showSmallVIPConfirm() {
                let that = this;
                this.$confirm({
                    title: '会员充值确认',
                    content: '您确定花费300元充值年度会员吗',
                    okText: '确定',
                    cancelText: '我再想想',
                    onOk() {
                        that.upgradeToSmallVIP();
                        that.set_upgradeToVIPVisible(false);
                    },
                    onCancel() {

                    },
                    class: 'test',
                });
            },
            showBigVIPConfirm() {
                let that = this;
                this.$confirm({
                    title: '大会员充值确认',
                    content: '您确定花费500元充值年度大会员吗',
                    okText: '确定',
                    cancelText: '我再想想',
                    onOk() {
                        that.upgradeToBigVIP();
                        that.set_upgradeToVIPVisible(false);
                    },
                    onCancel() {

                    },
                    class: 'test',
                });
            },
        },
    };
</script>

<style scoped>
    .focusCenter {
        text-align: center;
    }
</style>
