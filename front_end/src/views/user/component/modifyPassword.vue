<template>
    <a-modal
            @cancel="cancelSaveModifyPassword"
            @ok="saveModifyPassword"
            cancelText="取消"
            okText="保存"
            :visible="modifyPasswordVisible"
            title="修改密码"
    >
        <a-form :form="form">
            <a-form-item :label-col="{ span: 5 }" :wrapper-col="{ span: 15, offset: 1  }" label="原密码">
                <a-input-password
                        placeholder="请输入原密码"
                        v-decorator="[ 'oldPassword' , { rules: [{ required: true, validator: checkRight }],initialValue: '', validateTrigger: 'onBlur' }]"
                />
            </a-form-item>
            <a-form-item :label-col="{ span: 5 }" :wrapper-col="{ span: 15, offset: 1  }" label="新密码">
                <a-input-password
                        placeholder="请输入新密码"
                        v-decorator="[ 'newPassword' , { rules: [{ required: true, validator: checkSameOld}],initialValue: '',validateTrigger: 'onBlur' }]"
                />
            </a-form-item>
            <a-form-item :label-col="{ span: 5 }" :wrapper-col="{ span: 15, offset: 1  }" label="请确认">
                <a-input-password
                        placeholder="请再次输入新密码"
                        v-decorator="[ 'newPasswordAgain' , { rules: [{ required: true, validator: checkSame}],initialValue: '', validateTrigger: 'onBlur' }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';

    export default {
        name: 'modifyPassword',
        data() {
            return {
                form: this.$form.createForm(this, {name: 'modifyPassword'}),
            };
        },
        computed: {
            ...mapGetters([
                'modifyPasswordVisible',
                'userInfo',
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'modifyPassword'});
        },
        mounted() {
        },
        methods: {
            ...mapMutations([
                'set_modifyPasswordVisible',
            ]),
            ...mapActions([
                'updateUserPassword',
            ]),
            saveModifyPassword() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            username: this.form.getFieldValue('username'),
                            password: this.form.getFieldValue('newPasswordAgain'),
                        };
                        this.updateUserPassword(data).then(() => {
                            this.set_modifyPasswordVisible(false);
                        });
                    }
                });
            },
            cancelSaveModifyPassword() {
                this.set_modifyPasswordVisible(false);
            },
            checkSameOld(rule, value, callback) {
                if (value === this.userInfo.password) {
                    callback('密码没有发生变化');
                } else {
                    callback();
                }
            },
            checkRight(rule, value, callback) {
                if (value !== this.userInfo.password) {
                    callback('原密码输入错误，请重新输入');
                } else {
                    callback();
                }
            },
            checkSame(rule, value, callback) {
                if (value !== this.form.getFieldValue('newPassword')) {
                    callback('两次密码输入不一致，请重新输入');
                } else {
                    callback();
                }
            },
        },
    };

</script>

<style scoped>

</style>
