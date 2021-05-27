<template>
    <div class="main">
        <div class="top">
            <div class="header">
                <div>
                    <img src="@/assets/logo.png" class="logo" alt="logo">
                    <span class="title">Catel</span>
                </div>
            </div>
            <div class="desc">

            </div>
        </div>
        <a-form
                :form="form"
                class="user-layout-login"
                id="formLogin"
                ref="formLogin"
        >
            <a-tabs
                    :activeKey="customActiveKey"
                    :tabBarStyle="{ textAlign: 'center', borderBottom: 'unset' }"
                    @change="handleTabClick"
            >
                <a-tab-pane key="tab1" tab="账号密码登录">
                    <a-form-item>
                        <a-input
                                placeholder="邮箱或用户名"
                                size="large"
                                type="text"
                                autocomplete="off"
                                v-decorator="[
                'username',
                {rules: [{ required: true, message: '请输入邮箱地址或用户名' }], validateTrigger: 'blur'}
              ]"
                        >
                            <a-icon :style="{ color: 'rgba(0,0,0,.25)' }" slot="prefix" type="user"/>
                        </a-input>
                    </a-form-item>

                    <a-form-item>
                        <a-input-password
                                autocomplete="false"
                                placeholder="密码"
                                size="large"
                                v-decorator="[
                'password',
                {rules: [{ required: true, message: '请输入密码' }], validateTrigger: 'blur'}
              ]"
                        >
                            <a-icon :style="{ color: 'rgba(0,0,0,.25)' }" slot="prefix" type="lock"/>
                        </a-input-password>
                    </a-form-item>
                    <a-form-item>
                        <a-input
                                @keyup.enter.native="handlelogin()"
                                autocomplete="off"
                                placeholder="验证码"
                                size="large"
                                type="text"
                                v-decorator="[
                'captcha',
                {rules: [{ required: true, message: '请输入验证码' }], validateTrigger: 'blur'}
              ]"
                        ></a-input>
                        <div class="captcha">
                            <img :src="this.captchaParams.picBase" @click="refreshCaptcha()" alt="验证码"/>
                        </div>
                    </a-form-item>
                    <a-form-item style="margin-top:24px">
                        <a-button
                                :loading="loginLoading"
                                @click="handlelogin()"
                                class="login-button"
                                size="large"
                                type="primary"
                        >
                            <a-icon type="login"/>
                            登录
                        </a-button>
                    </a-form-item>
                </a-tab-pane>

                <a-tab-pane key="tab2" tab="注册新账号">
                    <a-form-item>
                        <a-input
                                autocomplete="off"
                                placeholder="邮箱"
                                size="large"
                                type="email"
                                v-decorator="[
              'registerUserMail',
              {rules: [{ required: true, type: 'email', message: '请输入邮箱' }], validateTrigger: 'blur'}]">
                            <a-icon :style="{ color: 'rgba(0,0,0,.25)' }" slot="prefix" type="mail"/>
                        </a-input>
                    </a-form-item>
                    <a-form-item>
                        <a-input
                                autocomplete="off"
                                placeholder="用户名"
                                size="large"
                                v-decorator="[
              'registerUsername',
              {rules: [{ required: true, message: '请输入用户名' }], validateTrigger: 'blur'}]">
                            <a-icon :style="{ color: 'rgba(0,0,0,.25)' }" slot="prefix" type="user"/>
                        </a-input>
                    </a-form-item>
                    <a-form-item>
                        <a-input
                                autocomplete="off"
                                placeholder="手机号"
                                size="large"
                                v-decorator="[
              'registerPhoneNumber',
              {rules: [{ required: true, message: '请输入手机号' }], validateTrigger: 'blur'}]">
                            <a-icon :style="{ color: 'rgba(0,0,0,.25)' }" slot="prefix" type="book"/>
                        </a-input>
                    </a-form-item>
                    <a-form-item>
                        <a-input-password
                                placeholder="密码"
                                size="large"
                                v-decorator="[
                'registerPassword',
                {rules: [{ required: true, message: '请输入密码' }, { validator: this.handlePassword }], validateTrigger: 'blur'}]">
                            <a-icon :style="{ color: 'rgba(0,0,0,.25)' }" slot="prefix" type="lock"/>
                        </a-input-password>
                    </a-form-item>
                    <a-form-item>
                        <a-input-password
                                placeholder="确认密码"
                                size="large"
                                v-decorator="[
                'registerPasswordconfirm',
                {rules: [{ required: true, message: '请再次输入密码' }, { validator: this.handlePasswordCheck }], validateTrigger: 'blur'}]"
                        >
                            <a-icon :style="{ color: 'rgba(0,0,0,.25)' }" slot="prefix" type="lock"/>
                        </a-input-password>
                    </a-form-item>
                    <a-form-item>
                        <a-input
                                @keyup.enter.native="handleRegister()"
                                autocomplete="off"
                                placeholder="验证码"
                                size="large"
                                type="text"
                                v-decorator="[
                'captcha',
                {rules: [{ required: true, message: '请输入验证码' }], validateTrigger: 'blur'}
              ]"
                        ></a-input>
                        <div class="captcha">
                            <img :src="this.captchaParams.picBase" @click="refreshCaptcha()" alt="验证码"/>
                        </div>
                    </a-form-item>
                    <a-form-item style="margin-top:24px">
                        <a-button
                                :loading="registerLoading"
                                @click="handleRegister()"
                                class="login-button"
                                size="large"
                                type="primary"
                        >注册
                        </a-button>
                    </a-form-item>
                </a-tab-pane>
            </a-tabs>
        </a-form>

    </div>
</template>

<script>
    import {mapActions, mapGetters} from 'vuex';

    export default {
        name: 'login',
        components: {},
        data() {
            return {
                customActiveKey: 'tab1',
                loginLoading: false,
                registerLoading: false,
                form: this.$form.createForm(this),
            };
        },
        computed: {
            ...mapGetters([
                'token',
                'captchaParams',
            ]),
        },
        mounted() {
            this.getCaptcha();
            console.log(this.captchaParams.picBase);
        },
        watch: {
            $route: {
                handler: function (route) {
                    this.redirect = route.query && route.query.redirect;
                },
                immediate: true,
            },
        },
        methods: {
            ...mapActions([
                'login',
                'register',
                'getCaptcha',
            ]),

            // handler
            handleUsernameOrEmail(rule, value, callback) {
                const {state} = this;
                const regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
                if (regex.test(value)) {
                    callback();
                } else {
                    callback(new Error('请输入有效用户名或邮箱'));
                }
                callback();
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
            refreshCaptcha() {
                this.getCaptcha();
            },
            handlePassword(rule, value, callback) {
                if (value.length < 6) {
                    callback(new Error('密码长度至少6位'));
                }
                callback();
            },
            handlePasswordCheck(rule, value, callback) {
                const password = this.form.getFieldValue('registerPassword');
                console.log(password);
                if (value === undefined) {
                    callback(new Error('请输入密码'));
                }
                if (value && password && value.trim() !== password.trim()) {
                    callback(new Error('两次密码不一致'));
                }
                callback();
            },
            handleTabClick(key) {
                this.customActiveKey = key;
            },
            handlelogin() {
                if (this.captchaParams.answer !== this.form.getFieldValue("captcha")) {
                    this.$message.error('验证码错误')
                } else {
                    const validateFieldsKey = this.customActiveKey === 'tab1' ? ['username', 'password'] : ['registerUsername', 'registerUserMail', 'registerPassword', 'registerPasswordconfirm'];
                    this.form.validateFields(validateFieldsKey, {force: true}, async (err, values) => {
                        if (!err) {
                            this.loginLoading = true;
                            const data = {
                                email: this.form.getFieldValue('username'),
                                password: this.form.getFieldValue('password'),
                            };
                            await this.login(data);
                            this.loginLoading = false;
                        }
                    });
                }
            },

            handleRegister() {
                if (this.captchaParams.answer !== this.form.getFieldValue('captcha')) {
                    this.$message.error('验证码错误');
                } else {
                    const {form: {validateFields}} = this;
                    const validateFieldsKey = this.customActiveKey === 'tab1' ? ['username', 'password'] : ['registerUsername', 'registerPhoneNumber', 'registerUserMail', 'registerPassword', 'registerPasswordconfirm'];
                    validateFields(validateFieldsKey, {force: true}, async (err, values) => {
                        if (!err) {
                            this.registerLoading = true;
                            const data = {
                                email: this.form.getFieldValue('registerUserMail'),
                                password: this.form.getFieldValue('registerPassword'),
                                phoneNumber: this.form.getFieldValue('registerPhoneNumber'),
                                username: this.form.getFieldValue('registerUsername'),
                                credit: 100,
                                userType: 0,
                            };
                            await this.register(data).then(() => {
                                this.form.resetFields();
                                this.customActiveKey = 'tab1';
                            });
                            this.registerLoading = false;
                        }
                    });
                }
            },
        },
    };
</script>

<style lang="less" scoped>

    .main {
        min-width: 260px;
        width: 368px;
        margin: 100px auto;

        .top {
            text-align: center;

            .header {
                height: 44px;
                line-height: 44px;

                .badge {
                    position: absolute;
                    display: inline-block;
                    line-height: 1;
                    vertical-align: middle;
                    margin-left: -12px;
                    margin-top: -10px;
                    opacity: 0.8;
                }

                .logo {
                    height: 44px;
                    vertical-align: top;
                    margin-right: 16px;
                    border-style: none;
                }

                .title {
                    font-size: 33px;
                    color: rgba(0, 0, 0, .85);
                    font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
                    font-weight: 600;
                    position: relative;
                    top: 2px;
                }
            }

            .desc {
                font-size: 14px;
                color: rgba(0, 0, 0, 0.45);
                margin-top: 12px;
                margin-bottom: 40px;
            }
        }

        .captcha {
            margin-top: 20px;
            text-align: center;
        }
    }

    .user-layout-login {
        label {
            font-size: 14px;
        }

        .getCaptcha {
            display: block;
            width: 100%;
            height: 40px;
        }

        .forge-password {
            font-size: 14px;
        }

        button.login-button {
            padding: 0 15px;
            font-size: 16px;
            height: 40px;
            width: 100%;
        }

        .user-login-other {
            text-align: left;
            margin-top: 24px;
            line-height: 22px;

            .item-icon {
                font-size: 24px;
                color: rgba(0, 0, 0, 0.2);
                margin-left: 16px;
                vertical-align: middle;
                cursor: pointer;
                transition: color 0.3s;

                &:hover {
                    color: #1890ff;
                }
            }

            .register {
                float: right;
            }
        }
    }
</style>
