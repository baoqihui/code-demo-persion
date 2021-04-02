<template>
  <div id="pagers">
    <el-pagination
      background
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page.sync="pageNumber"
      :page-size="pageSize"
      :page-sizes="pageSizesArray"
      prev-text="上一页"
      next-text="下一页"
      :total="total"
      :pager-count="pagercounted"
      :layout="layout"
    ></el-pagination>
  </div>
</template>

<script>
export default {
  name: "datapage",
  props: ["pageSize", "total", "currentPage", "pagercounted", "layout", "pageSizesArray"],
  data() {
    return {
      pageNumber: this.currentPage
    };
  },
  computed: {},
  watch: {
    currentPage(newVal,oldVal){
      this.pageNumber = newVal
    }
  },
  methods: {
    handleSizeChange(val) {
      this.$emit("handleSizeChange", val);
    },
    handleCurrentChange(val) {
      this.$emit("handleCurrentChange", val);
      this.pageNumber = val;
    }
  },
};
</script>

<style>
#pagers {
  width: 100%;
  margin-top: 20px;
  /* text-align: center; */
}
.btn-prev,
.btn-next {
  width: 64px !important;
}
</style>
