<template>
    <a-modal
        :closable="false"
        :visible="modifyUserInfoListVisible"
        title="修改信息"
    >
        <a-form :form="form">
            <a-form-item :label-col="{ span: 5 }" :wrapper-col="{ span: 8, offset: 1  }" label="用户姓名">
                <span>{{this.currentPersonInfo.realName}}</span>
            </a-form-item>
            <a-form-item :label-col="{ span: 5 }" :wrapper-col="{ span: 10, offset: 1  }" label="用户手机号">
                <a-input
                    placeholder="请填写用户手机号"
                    v-decorator="[ 'phoneNumber' , { rules: [{ required: true, message: '请输入用户手机号' }],initialValue: this.currentPersonInfo.phoneNumber }]"
                    autocomplete="off"
                    v-if="modify"
                />
                <span v-else>{{this.currentPersonInfo.phoneNumber}}</span>
            </a-form-item>
            <a-form-item :label-col="{ span: 5 }" :wrapper-col="{ span: 8, offset: 1  }" label="身份证号码">
                <span>{{this.currentPersonInfo.idNo}}</span>
            </a-form-item>
            <a-form-item :label-col="{ span: 5 }" :wrapper-col="{ span: 8, offset: 1  }" label="用户生日">
                <span>{{this.currentPersonInfo.birthday}}</span>
            </a-form-item>
        </a-form>
        <template slot="footer">
            <a-button @click="saveModifyUserInfo" type="primary" v-if="modify">保存</a-button>
            <a-button @click="modifyUserInfo" type="primary" v-else>修改</a-button>
            <a-button @click="cancelSaveModifyUserInfo" type="info" v-if="modify">取消</a-button>
            <a-button @click="cancelModifyUserInfo" type="info" v-else>完成</a-button>
        </template>

    </a-modal>

</template>

<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';

    export default {
        name: 'modifyUserInfo',
        data() {
            return {
                modify: false,
                form: this.$form.createForm(this, {name: 'modifyUserInfo'}),
                modifyUserInfoVisible: false,
            };

        },
        computed: {
            ...mapGetters([
                'modifyUserInfoListVisible',
                'currentPersonInfo',
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'modifyUserInfo'});
        },
        mounted() {
        },
        methods: {
            ...mapMutations([
                'set_modifyUserInfoListVisible',
                'set_addUserInfoParams',
                'set_currentPersonInfo',
            ]),
            ...mapActions([
                'updateUserInfo',
                'updatePersonInfo',
            ]),
            modifyUserInfo() {
                this.form.setFieldsValue({
                    'realName': this.currentPersonInfo.realName,
                    'phoneNumber': this.currentPersonInfo.phoneNumber,
                    'idNo': this.currentPersonInfo.idNo,
                    'birthday': this.currentPersonInfo.birthday,
                });
                this.modify = true;
            },
            cancelModifyUserInfo() {
                this.modify = false;
                this.set_modifyUserInfoListVisible(false);
            },
            saveModifyUserInfo() {
                console.log(this.form.getFieldValue('realName'));
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            phoneNumber: this.form.getFieldValue('phoneNumber')
                        };
                        this.set_currentPersonInfo(data);
                        this.updatePersonInfo();
                        this.modify = false;
                    }
                });
            },
            cancelSaveModifyUserInfo() {
                this.modify = false;
                this.set_modifyUserInfoListVisible(false);
            },
        },
    };

</script>

<style scoped>

</style>
