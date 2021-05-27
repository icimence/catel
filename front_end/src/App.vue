<template>
    <a-config-provider :locale="locale">
        <div data-title="Catel" id="app" v-title>
            <transition mode="out-in" name="fade-transform">
                <router-view/>
            </transition>
        </div>
    </a-config-provider>
</template>

<script>
    import zh_CN from 'ant-design-vue/lib/locale-provider/zh_CN';

    export default {

        components: {},
        name: 'App',
        data() {
            return {
                locale: zh_CN,
            };
        },
        created() {
            //在页面加载时读取sessionStorage里的状态信息
            if (sessionStorage.getItem('store')) {
                this.$store.replaceState(Object.assign({}, this.$store.state, JSON.parse(sessionStorage.getItem('store'))));
            }

            //在页面刷新时将vuex里的信息保存到sessionStorage里
            window.addEventListener('beforeunload', () => {
                sessionStorage.setItem('store', JSON.stringify(this.$store.state));
            });
        },
        methods: {
        },
    };
</script>

<style>
    #app {
        font-family: 'Yuanti SC', 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        color: #2c3e50;
        width: 100%;
        padding: 20px 100px 144px;
        background: #f0f2f5;
        min-height: 100%
    }

    #nav {
        padding: 30px;
    }

    #nav a {
        font-weight: bold;
        color: #2c3e50;
    }

    #nav a.router-link-exact-active {
        color: #42b983;
    }

    /* fade-transform */
    .fade-transform-leave-active,
    .fade-transform-enter-active {
        transition: all .5s;
    }

    .fade-transform-enter {
        opacity: 0;
        transform: translateX(-30px);
    }

    .fade-transform-leave-to {
        opacity: 0;
        transform: translateX(30px);
    }
</style>
