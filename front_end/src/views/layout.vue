<template>
    <a-locale-provider :locale="this.zh_CN">
        <div id="layout">
            <Header></Header>
            <transition mode="out-in" name="fade-transform">
                <router-view />
            </transition>
        </div>
    </a-locale-provider>
</template>

<script>
    import Header from '@/components/header';

    export default {
        name: 'layout',
        components: {
            Header,
        },
    };
</script>
