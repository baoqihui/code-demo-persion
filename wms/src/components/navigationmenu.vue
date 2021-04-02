<template>
  <el-menu
    :default-active="DefaultActive"
    class="el-menu-vertical-demo"
    unique-opened
    background-color="#304156"
    text-color="#fff"
    @open="handleOpen"
    @close="handleClose"
    @select="handelSelect"
  >
    <template v-for="(item,i) in menuInfo">
      <el-submenu :key="i" :index=" item.href || ((i+1) + '')" v-if="item.children.length>0">
        <!-- 此处为导航 一级菜单 -->
        <template slot="title">
          <!-- <i :class="item.icon" style="color:#fff;"></i> -->
          <span slot="title" style="margin-left:10px;">{{item.title}}</span>
        </template>
        <!-- 如果二级菜单不为空 -->
        <template v-if="item.HasChild">
          <template v-for="(subItem,index) in item.children">
            <!-- 此处二级菜单 -->
            <!-- 如果三级菜单不为空执行 -->
            <template v-if="subItem.HasChild">
              <el-submenu :index="subItem.href || index + ''" :key="index">
                <span
                  slot="title"
                  style="padding-left:10px !important;font-size:14px;color:#efefef"
                >
                  <i :class="subItem.icon"></i>
                  {{subItem.title}}
                </span>
                <template v-if="subItem.HasChild">
                  <template v-for="(subItemSub,subIndex) in subItem.children">
                    <!-- 此处为三级菜单 -->
                    <!-- 如果四级菜单不为空执行 -->
                    <template v-if="subItemSub.HasChild">
                      <el-submenu :index="subItemSub.href || subIndex + ''" :key="subIndex">
                        <span slot="title" style="font-size:14px;color:#e5e5e5">
                          <i :class="subItemSub.icon"></i>
                          {{subItemSub.title}}
                        </span>
                        <!-- 此处为四级菜单 -->
                        <el-menu-item-group>
                          <el-menu-item
                            :index="subItemSunSub.href || subIndexSub + ''"
                            v-for="(subItemSunSub,subIndexSub) in subItemSub.children"
                            :key="subIndexSub"
                          >
                            <span :href="item.href" style="font-size:12px;color:#e0e0e0;">
                              <i :class="subItemSunSub.icon"></i>
                              {{subItemSunSub.title}}
                            </span>
                          </el-menu-item>
                        </el-menu-item-group>
                      </el-submenu>
                    </template>
                    <!-- 如果四级菜单为空时执行 -->
                    <template v-else>
                      <!-- <el-menu-item :index="'/home/users'" :key="subIndex"> -->
                      <el-menu-item :index="subItemSub.href || subIndex + ''" :key="subIndex">
                        <span :href="item.href">
                          <i :class="subItemSub.icon"></i>
                          {{subItemSub.title}}
                        </span>
                      </el-menu-item>
                    </template>
                  </template>
                </template>
                <template v-else>
                  <el-menu-item-group>
                    <el-menu-item
                      :index="subItemSub.href || subIndex + ''"
                      v-for="(subItemSub,subIndex) in subItem.children"
                      :key="subIndex"
                    >
                      <span slot="title" style="font-size:14px;color:#c5c5c5">
                        <i :class="subItemSub.icon"></i>
                        {{subItemSub.title}}
                      </span>
                    </el-menu-item>
                  </el-menu-item-group>
                </template>
              </el-submenu>
            </template>
            <!-- 如果三级菜单为空执行 -->
            <template v-else>
              <el-menu-item :index="subItem.href || index + ''" :key="index">
                <span
                  slot="title"
                  style="padding-left:10px !important;font-size:14px;color:#e5e5e5"
                >
                  <span :href="subItem.href">
                    <i :class="subItem.icon"></i>
                    {{subItem.title}}
                  </span>
                </span>
              </el-menu-item>
            </template>
          </template>
        </template>
        <!-- 如果二级菜单为空 -->
        <template v-else>
          <el-menu-item
            :index="subItem.href || i + ''"
            v-for="(subItem,i) in item.children"
            :key="i"
          >
            <span slot="title" style="padding-left:10px !important;font-size:14px;color:#e5e5e5">
              <i :class="subItem.icon" style="color:#fff;"></i>
              {{subItem.title}}
            </span>
          </el-menu-item>
        </template>
      </el-submenu>
      <!-- 如果一级菜单栏为空 -->
      <el-menu-item v-else :key="i" :index="item.href || i + ''" style="font-size:14px;">
        <i :class="item.icon" style="color:#fff !important"></i>
        <span slot="title" style="margin-left:10px;">{{item.title}}</span>
      </el-menu-item>
    </template>
  </el-menu>
</template>
<style scoped>
.el-menu {
  border-right: none !important;
}
.el-submenu .el-menu-item {
  background-color: rgba(0, 0, 0, 0.3) !important;
}
.el-menu-item:hover {
  background-color: rgba(0, 0, 0, 0.8) !important;
}

.el-menu-item:hover,
.el-menu-item:active,
.el-menu-item:focus {
  background: #189aff !important;
}
.el-menu-item.is-active {
  color: #fff !important;
  background: #189aff !important;
}
.muenlist span{
  font-size: 15px;
}
</style>

<script>
export default {
  name: "menulist",
  data() {
    return {
      menuInfo: [],
    };
  },
  props: ['isCollapse'],
  computed: {
    // openList() {
    //   var tempList = [];
    //   let menuList = JSON.parse(localStorage.getItem('menuList'))[0].children
    //   menuList.forEach(v => {
    //     tempList.push(v.href)
    //   });
    //   return tempList;
    // },
    // 路由自动选择
    DefaultActive() {
      var route = this.$route.path.replace(/^\/PM/, '')
      if (this.$route.meta.requiresAuth) {
        this.$store.dispatch('addActiveList',{name:this.$route.meta.title,path:this.$route.path}) 
      }
      return route
    }
  },
  created(){

    if(!this.menuInfo.length){
      setTimeout(()=>{
        this.menuInfo = JSON.parse(localStorage.getItem('menuList'))
      }, 100)
    }
    this.$forceUpdate()
  },
  methods: {
    handleOpen(key, keyPath) {

    },
    handleClose(key, keyPath) {

    },
    handelSelect(key, keyPath, e) { 
      let paths = ""
      paths = key
      var routerName = key.substr(key.toString().lastIndexOf("/"));
      this.$router.push(paths);
    },
  },
};
</script>