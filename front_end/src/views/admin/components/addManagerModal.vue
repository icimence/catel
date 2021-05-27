<template>
    <a-modal
            :visible="addManagerModalVisible"
            @cancel="cancel"
            @ok="handleSubmit"
            cancelText="取消"
            okText="确定"
            title="添加酒店管理员"
    >
        <a-form :form="form">
            <a-form-item label="用户类型" v-bind="formItemLayout">
                <span>酒店管理员</span>
            </a-form-item>
            <a-form-item label="用户邮箱" v-bind="formItemLayout">
                <a-input
                        autocomplete="off"
                        v-decorator="[
                        'email',
                        { rules: [{required: true, message: '请输入用户邮箱'}] }
                    ]"
                />
            </a-form-item>
            <a-form-item label="用户名" v-bind="formItemLayout">
                <a-input
                        autocomplete="off"
                        v-decorator="[
                        'username',
                        { rules: [{required: true, message: '请输入用户名', }] }
                    ]"
                />
            </a-form-item>
            <a-form-item label="密码" v-bind="formItemLayout">
                <a-input
                        autocomplete="off"
                        v-decorator="[
                        'password',
                        { rules: [{required: true, message: '请输入密码', }] }
                    ]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';

    export default {
        name: 'addManagerModal',
        data() {
            return {
                formItemLayout: {
                    labelCol: {
                        xs: {span: 12},
                        sm: {span: 6},
                    },
                    wrapperCol: {
                        xs: {span: 24},
                        sm: {span: 16},
                    },
                },
            };
        },
        computed: {
            ...mapGetters([
                'addManagerModalVisible',
                'managerList',
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'addManagerModal'});
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addManagerModalVisible',
                'set_addManagerParams',
            ]),
            ...mapActions([
                'getManagerList',
                'addManager',
            ]),
            cancel() {
                this.set_addManagerModalVisible(false);
            },
            checkEmail(rule, value, callback) {
                const re = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
                if (re.test(value)) {
                    callback();
                } else {
                    callback(new Error('请输入有效邮箱'));
                }
                callback();
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            email: this.form.getFieldValue('email'),
                            password: this.form.getFieldValue('password'),
                            username: this.form.getFieldValue('username'),
                        };
                        this.set_addManagerParams(data);
                        this.addManager();
                        this.form.resetFields();
                    }
                });
            },
        },
    };
</script>
