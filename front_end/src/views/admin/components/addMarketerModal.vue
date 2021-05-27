<template>
    <a-modal
            :visible="addMarketerModalVisible"
            @cancel="cancel"
            @ok="handleSubmit"
            cancelText="取消"
            okText="确定"
            title="添加网站营销人员"
    >
        <a-form :form="form">
            <a-form-item label="用户类型" v-bind="formItemLayout">
                <span>网站营销人员</span>
            </a-form-item>
            <a-form-item label="用户邮箱" v-bind="formItemLayout">
                <a-input
                        autocomplete="off"
                        v-decorator="[
                        'email',
                        { rules: [{required: true, message: '请输入用户邮箱', }] }
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
        name: "addMarketerModal",
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
                'addMarketerModalVisible',
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'addMarketerModal'});
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addMarketerModalVisible',
                'set_addMarketerParams',
            ]),
            ...mapActions([
                'addMarketer',
            ]),
            cancel() {
                this.set_addMarketerModalVisible(false);
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
                        console.log(data);
                        this.set_addMarketerParams(data);
                        this.addMarketer();
                        this.form.resetFields();
                    }
                });
            },
        },
    }
</script>

<style scoped>

</style>
