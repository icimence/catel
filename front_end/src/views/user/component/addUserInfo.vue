<template>
    <a-modal
        :visible="addUserInfoVisible"
        @cancel="cancel"
        @ok="addUserInfoSave"
        cancelText="取消"
        okText="确定"
        title="添加用户信息"
    >
        <a-form :form="form">
            <a-form-item :label-col="{ span: 5 }" :wrapper-col="{ span: 11, offset: 1  }" label="用户名">
                <a-input
                    autocomplete="off"
                    placeholder="请填写用户名"
                    v-decorator="['userName', { rules: [{ required: true, message: '请输入用户名' }] }]"
                />
            </a-form-item>
            <a-form-item :label-col="{ span: 5 }" :wrapper-col="{ span: 11, offset: 1  }" label="身份证号">
                <a-input
                    autocomplete="off"
                    placeholder="请填写身份证号"
                    v-decorator="['IDNumber', { rules: [{ required: true, message: '请输入手机号' }] }]"
                />
            </a-form-item>
            <a-form-item :label-col="{ span: 5 }" :wrapper-col="{ span: 11, offset: 1  }" label="手机号">
                <a-input
                    autocomplete="off"
                    placeholder="请填写手机号"
                    v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' }] }]"
                />
            </a-form-item>
            <a-form-item :label-col="{ span: 5 }" :wrapper-col="{ span: 15, offset: 1  }" label="生日">
                <a-date-picker
                    placeholder="请填写生日"
                    v-decorator="['birthday', { rules: [{ required: true, message: '请输入生日' }] }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';

    const moment = require('moment');

    export default {
        name: 'addUserInfo',
        data() {
            return {
                form: this.$form.createForm(this, {name: 'addUserInfo'}),
            };
        },
        computed: {
            ...mapGetters([
                'addUserInfoVisible',
                'userId',
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'addUserInfo'});
        },
        mounted() {
        },
        methods: {
            ...mapMutations([
                'set_addUserInfoVisible',
                'set_addUserInfoParams',
            ]),
            ...mapActions([
                'addUserInfo',
            ]),
            cancel() {
                this.set_addUserInfoVisible(false);
            },
            addUserInfoSave(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            userId: this.userId,
                            realName: this.form.getFieldValue('userName'),
                            idNo: this.form.getFieldValue('IDNumber'),
                            phoneNumber: this.form.getFieldValue('phoneNumber'),
                            birthday: moment(this.form.getFieldValue('birthday')).format('YYYY-MM-DD'),
                        };
                        this.set_addUserInfoParams(data);
                        this.addUserInfo();
                        this.form.resetFields();
                    }
                });
            },
        },
    };
</script>
