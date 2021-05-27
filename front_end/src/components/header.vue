<template>
    <div class="header">
        <div class="label">
            <img @click="jumpToHome" alt="logo" class="logo" src="@/assets/logo.png">
            <span class="title">Catel</span>
        </div>
        <a-menu mode="horizontal" theme="light" v-model="current">
            <a-menu-item @click="selectMenu" key="1" >
                <router-link to="/hotel/hotelList">
                    <a-icon type="compass" />
                    首页
                </router-link>
            </a-menu-item>
            <a-menu-item @click="selectMenu" key="2">
                <router-link to="/user/info/:userId">
                    <a-icon type="user" />
                    个人中心
                </router-link>
            </a-menu-item>
            <a-menu-item @click="selectMenu" key="3" v-if="userInfo.userType==='HotelManager'">
                <router-link :to="{ name: 'manageHotel'}">
                    <a-icon type="bank" />
                    酒店管理
                </router-link>
            </a-menu-item>
            <a-menu-item @click="selectMenu" key="4" v-if="userInfo.userType==='Admin'">
                <router-link :to="{ name: 'manageUser'}">
                    <a-icon type="crown" />
                    系统管理
                </router-link>
            </a-menu-item>
            <a-menu-item @click="selectMenu" key="5" v-if="userInfo.userType==='Marketer'">
                <router-link :to="{ name: 'manageMarket'}">
                    <a-icon type="gift" />
                    市场管理
                </router-link>
            </a-menu-item>
        </a-menu>
        <div class="logout">
            <a-dropdown placement="bottomCenter">
                <div class="user">
                    <a-avatar :src="this.userInfo.avatar"></a-avatar>
                    <span class="smallVIPNameDisplay" v-if="this.userInfo.vipType === 'Small'">{{ userInfo.username }}</span>
                    <span class="bigVIPNameDisplay" v-if="this.userInfo.vipType === 'Big'">{{ userInfo.username }}</span>
                    <span class="UserNameDisplay" v-if="this.userInfo.vipType === 'Nil'">{{ userInfo.username }}</span>
                    <a-icon style="margin-left: 3px; font-size: 16px" type="down"></a-icon>
                </div>
                <a-menu slot="overlay">
                    <a-menu-item @click="jumpToHome()">
                        <a-icon type="home"></a-icon>
                        首页
                    </a-menu-item>
                    <a-menu-item @click="jumpToUserInfo()">
                        <a-icon type="profile"></a-icon>
                        我的信息
                    </a-menu-item>

                    <a-menu-item @click="showLogoutConfirm()">
                        <a-icon type="poweroff" />
                        退出登录
                    </a-menu-item>
                </a-menu>
            </a-dropdown>
        </div>

    </div>

</template>

<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';

    export default {
        name: '',
        data() {
            return {
                current: ['1'],
            };
        },
        created() {
        },
        computed: {
            ...mapGetters([
                'userId',
                'userInfo',
            ]),
        },
        watch: {
            $route() {
                console.log(this.$route.name);
                if (this.$route.name === 'hotelList' || this.$route.name === 'hotelDetail') {
                    this.current = ['1'];
                } else if (this.$route.name === 'userInfo') {
                    this.current = ['2'];
                } else if (this.$route.name === 'manageHotel') {
                    this.current = ['3'];
                } else {
                    this.current = ['4'];
                }
            },
        },
        mounted() {
            if (this.$route.name === 'hotelList' || this.$route.name === 'hotelDetail') {
                this.current = ['1'];
            } else if (this.$route.name === 'userInfo') {
                this.current = ['2'];
            } else if (this.$route.name === 'manageHotel') {
                this.current = ['3'];
            } else {
                this.current = ['4'];
            }
        },
        methods: {
            ...mapMutations([]),
            ...mapActions([
                'logout',
            ]),
            selectMenu(v) {
            },
            async quit() {
                await this.$store.dispatch('logout');
                this.$router.push(`/login?redirect=${this.$route.fullPath}`);
            },
            jumpToUserInfo() {
                this.$router.push({name: 'userInfo', params: {userId: this.userId}});
            },
            jumpToHome() {
                this.$router.push({name: 'hotelList'});
            },
            showLogoutConfirm() {
                const that = this;
                this.$confirm({
                    title: '确认',
                    content: '您确定要退出吗',
                    okText: '退出',
                    okType: 'danger',
                    cancelText: '取消',
                    async onOk() {
                        await that.$store.dispatch('logout');
                        that.$router.push(`/login?redirect=${that.$route.fullPath}`);
                    },
                    onCancel() {
                    },

                });
            },
        },
    };
</script>

<style lang="less" scoped>
    .header {
        display: flex;
        line-height: 44px;
        height: 44px;
        align-items: center;
        justify-content: space-between;
        min-width: 800px;

        .label {
            height: 44px;
            line-height: 44px;
            vertical-align: middle;
            min-width: 400px;

            .logo {
                height: 44px;
                vertical-align: top;
                margin-right: 16px;
                border-style: none;
                cursor: pointer;
            }

            .title {
                font-size: 33px;
                color: rgba(0, 0, 0, .85);
                font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
                font-weight: 600;
                position: relative;
                top: 2px;
            }

            .search {
                width: 300px;
                margin-left: 30px
            }
        }

        .logout {
            margin-right: 40px;

            .user {
                cursor: pointer;
                display: flex;
                align-items: center;

                span {
                    margin-left: 5px
                }
            }
        }

        .smallVIPNameDisplay {
            font-size: 16px;
            color: red;
            /*text-shadow: 0px 3px 3px #83ACDB;*/
        }

        .bigVIPNameDisplay {
            font-size: 16px;
            color: #eec710;
            /*text-shadow: 0px 3px 3px #83ACDB;*/
        }

        .UserNameDisplay {
            font-size: 14px;
        }

    }
</style>

<style lang="less">
    .header {
        .ant-menu {
            background: none
        }
    }
</style>
