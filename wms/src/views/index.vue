<template>
    <div id="index">
        <keep-alive>
            <router-view v-if="$route.meta.keepAlive" />
        </keep-alive>
        <router-view v-if="!$route.meta.keepAlive"></router-view>
    </div>
</template>
<script>
export default {
    data(){
        return{
        }
    },
}
</script>
<style scoped>
#index{
    width: 100%;
    height: calc(100% - 50px);
}
</style>