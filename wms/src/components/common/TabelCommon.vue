<template>
  <div id="TableCommon">
    <el-table
      class="TableCommon"
      stripe
      ref="refTab"
      row-key="id"
      :data="TabularData"
      :height="height"
      :max-height="maxHeight"
      v-loading="loading"
      element-loading-text="玩命加载中"
      :border="border"
      :header-cell-style="{ background: '#f8f8f8' }"
      :default-sort="{ prop: 'amount', order: 'descending' }"
      :cell-style="{ height: '55px', padding: '0' }"
      highlight-current-row
      :show-summary='showSummary'
      @selection-change="handleSelectionChange"
      @select="handleSelection"
      @row-click="rowClick"
      @cell-click="cellClick"
      @row-dblclick="rowDblclick"
    >
      <el-table-column
        type="selection"
        v-if="isChoice"
        width="40"
        :selectable="checkSelectable"
        :reserve-selection="true"
      ></el-table-column>
      <el-table-column
        type="index"
        label="序号"
        v-if="isXhShow"
        width="50"
        align="center"
      ></el-table-column>
      <el-table-column
        v-for="(item, index) in TableHeader"
        :key="index"
        :label="item.label"
        :prop="item.type"
        :width="item.width"
        min-width="120px"
        align="center"
        :show-overflow-tooltip="true"
        sortable
      >
        <template slot-scope="scope">
          <div v-if="item.type == 'qcFlag'">
            <span>{{ scope.row[item.type] == "Y" ? "是" : "否" }}</span>
          </div>
          <div v-else-if="item.index>=0">
            <el-input 
              v-model.trim="scope.row[item.type]"
              size="mini"
              @blur="blurIndex(scope.$index,scope.row,item.index,scope.row[item.type],item.type)"
              @focus="focusIndex( scope.row[item.type] )"
            ></el-input>
          </div> 
          <div v-else-if="item.type == 'state'">
            <span>{{
              scope.row[item.type]
                ? "正常"
                :  "停用" 
            }}</span>
          </div> 
          <div v-else-if="item.ischangeTime">
            <el-date-picker
              v-model="scope.row[item.type]"
              type="date"
              size="mini"
              style="width: 150px"
              @change="changeTime(scope.row, scope.row[item.type])"
              value-format="yyyy-MM-dd"
              placeholder="选择日期"
            >
            </el-date-picker>
          </div>
          <div v-else>
            <span>{{ scope.row[item.type] }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="150"
        header-align="center"
        align="center"
        class-name="option"
        v-if="
          iscompile ||
          isadd ||
          isremove ||
          isexamine ||
          isAddone ||
          ischeckout ||
          isaudit ||
          isConfirm ||
          isbuildSolution ||
          isinStorage ||
          isshipments ||
          isGenerate ||
          isPrint || 
          isDownload ||
          isbarM ||maintain||amend
        "
      >
        <template slot-scope="scope">
          <el-button
            v-if="iscompile"
            @click.stop="compile(scope.$index, scope.row)"
            type="text"
            size="small"
            >编辑</el-button
          >
          <el-button
            v-if="isamend"
            @click.stop="amend(scope.$index, scope.row)"
            type="text"
            size="small"
            >修改</el-button
          >
          <el-button
            v-if="isexamine"
            @click.stop="examine(scope.$index, scope.row)"
            type="text"
            size="small"
            >查看</el-button
          >  
          <el-button
            v-if="maintain"
            @click.stop="maintainItem(scope.$index, scope.row)"
            type="text"
            size="small"
            :style="{color:(scope.row.isExceedTime==1?'red':'')}"
            >保养</el-button
          >
          <el-button
            v-if="isbarM"
            @click.stop="barM(scope.$index, scope.row)"
            type="text"
            size="small"
            >生成条码</el-button
          >
          <el-button
            v-if="isshipments && scope.row.reloadStatus == '1'"
            @click.stop="shipments(scope.$index, scope.row)"
            type="text"
            size="small"
            >发货</el-button
          >
          <el-button
            v-if="isinStorage && scope.row.reloadStatus == '2'"
            @click.stop="inStorage(scope.$index, scope.row)"
            type="text"
            size="small"
            >入库</el-button
          >
          <el-button
            v-if="ischeckout"
            @click.stop="checkout(scope.$index, scope.row)"
            type="text"
            size="small"
            >检验</el-button
          >
          <el-button
            v-if="isaudit"
            @click.stop="audit(scope.$index, scope.row)"
            type="text"
            size="small"
            >审核</el-button
          > 
          <el-button
            v-if="isbuildSolution"
            @click.stop="buildSolution(scope.$index, scope.row)"
            type="text"
            size="small"
            >生成方案</el-button
          >
          <el-button
            v-if="isadd"
            @click.stop="add(scope.$index, scope.row)"
            type="text"
            size="small"
            >新增</el-button
          >
          <el-button
            v-if="isAddone"
            @click.stop="addData(scope.$index, scope.row)"
            size="small"
            type="text"
            >添加</el-button
          >
          <el-button
            v-if="isConfirm"
            @click.stop="confirm(scope.$index, scope.row)"
            size="small"
            type="text"
            >确定</el-button
          >
          <el-button
            v-if="isGenerate"
            @click.stop="generate(scope.$index, scope.row)"
            size="small"
            type="text"
            >生成</el-button
          >
          <el-button
            v-if="isPrint"
            @click.stop="print(scope.$index, scope.row)"
            size="small"
            type="text"
            >打印</el-button
          > 
          <el-button
            v-if="isDownload"
            @click.stop="download(scope.$index, scope.row)"
            size="small"
            type="text"
            >下载</el-button
          > 
          <el-button
            v-if="isremove"
            @click.stop="remove(scope.$index, scope.row)"
            type="text"
            size="small"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <div
      style="
        margin-top: 15px;
        text-align: center;
        padding-bottom: 15px;
        width: 100%;
      "
      v-if="isPager"
    >
      <Datapages
        :pageSize="pageSize"
        :total="recordsCount"
        :currentPage="currentPage"
        :pagercount="pagercount"
        :layout="layout"
        @handleCurrentChange="handleCurrentChange"
        @handleSizeChange="handleSizeChange"
      ></Datapages>
    </div>
  </div>
</template>
<script>
export default {
  name: "TableCommon",
  components: {
    Datapages: () => import("./Datapage"),
  },
  props: {
    TableHeader: {},
    TabularData: { type: [Array, Object] },
    id: "",
    // ref
    refTab: {
      type: String,
      default: "",
    },
    sortable: {
      type: Boolean,
      default: false,
    },
    // 是否多选
    isChoice: {
      type: Boolean,
      default: false,
    },
    // 控制是否能够选中
    canChoice: {
      type: Boolean,
      default: true,
    },
    // 固定表头
    height: {
      type: String,
      // default: "650px",
    },
    // 最大高度
    maxHeight: {
      type: String,
      // default: '500px',
    },
    // 是否使用序号
    isXhShow: {
      type: Boolean,
      default: true,
    },
    // 合计
    showSummary:{
      type: Boolean,
      default: false
    },
    // 表格线条
    border: {
      type: Boolean,
      default: true,
    },
    // 是否开启分页
    isPager: {
      type: Boolean,
      default: true,
    },
    // 每页条数
    pageSize: {
      type: Number,
      default: 10,
    },
    // 分页总条数
    recordsCount: {
      type: Number,
      default: 0,
    },
    // 当前页
    pagercount: {
      type: Number,
      default: 1,
    },
    currentPage: {
      type: Number,
      default: 1,
    },
    // 分页功能
    layout: {
      type: String,
      default: "sizes,total, prev, pager, next, jumper",
    },
    // 加载中
    loading: {
      type: Boolean,
      default: false,
    },
    // 修改功能开启
    isamend: {
      type: Boolean,
      default: false,
    },
    // 编辑功能开启
    iscompile: {
      type: Boolean,
      default: false,
    },
    // 查看功能开启
    isexamine: {
      type: Boolean,
      default: false,
    },  
    // 保养功能开启
    maintain: {
      type: Boolean,
      default: false,
    },
    // 其他入库条码管理功能开启
    isbarM: {
      type: Boolean,
      default: false,
    },
    // 退换出库中的发货
    isshipments: {
      type: Boolean,
      default: false,
    },
    // 退换出库中的入库
    isinStorage: {
      type: Boolean,
      default: false,
    },
    // 检验功能开启
    ischeckout: {
      type: Boolean,
      default: false,
    },
    // 审核功能开启
    isaudit: {
      type: Boolean,
      default: false,
    }, 
    // 生成方案功能开启
    isbuildSolution: {
      type: Boolean,
      default: false,
    },
    // 新增功能开启
    isadd: {
      type: Boolean,
      default: false,
    },
    // 删除
    isremove: {
      type: Boolean,
      default: false,
    }, 
    // 新增一天tr
    isAddone: {
      type: Boolean,
      default: false,
    },
    // 确认修改
    isConfirm: {
      type: Boolean,
      default: false,
    },
    // 销售发货管理 //// 生成 打印   回传
    isGenerate: {
      type: Boolean,
      default: false,
    },
    isPrint: {
      type: Boolean,
      default: false,
    }, 
    isDownload: {
      type: Boolean,
      default: false,
    }, 
  },
  // watch:{
  //   TabularData:{
  //     handler(newValue, oldValue) {
  //       console.log(newValue)
  //     }
  //   }
  // },
  methods: {
    toggleAllSelect() {
      this.$nextTick(() => {
        this.$refs.refTab.toggleRowSelection(this.TabularData[0], true);
      });
    },
    // 清空选择的值
    clearSelect () {
      this.$refs.refTab.clearSelection()
    },
    sortDevName(a){
    },
    checkSelectable(row) { 
      return true
    },
    handleSelectionChange(val) {
      this.$emit("handleSelectionChange", val);
    },
    handleSelection(select, row) {
      this.$emit("handleSelection", { select, row });
    },
    amend(index, row) {
      this.$emit("amendModel", { index, row });
    },
    compile(index, row) {
      this.$emit("CompileModel", { index, row });
    },
    examine(index, row) {
      this.$emit("ExamineModel", { index, row });
    }, 
    maintainItem(index, row) {
      this.$emit("maintainItem", { index, row });
    },
    barM(index, row) {
      this.$emit("BarMModel", { index, row });
    },
    shipments(index, row) {
      this.$emit("shipmentsModel", { index, row });
    },
    inStorage(index, row) {
      this.$emit("inStorageModel", { index, row });
    },
    checkout(index, row) {
      this.$emit("checkoutModel", { index, row });
    },
    audit(index, row) {
      this.$emit("auditModel", { index, row });
    }, 
    buildSolution(index, row) {
      this.$emit("buildSolutionModel", { index, row });
    },
    add(index, row) {
      this.$emit("AddModel", { index, row });
    },
    remove(index, row) {
      this.$emit("RemoveModel", { index, row });
    }, 
    addData(index, row) {
      this.$emit("AddDataModel", { index, row });
    },
    confirm(index, row) {
      this.$emit("ConfirmModel", { index, row });
    },
    generate(index, row) {
      this.$emit("GenerateModel", { index, row });
    },
    print(index, row) {
      this.$emit("PrintModel", { index, row });
    }, 
    download(index, row) {
      this.$emit("DownloadModel", { index, row });
    }, 
    changeto(index, row, type) {
      this.$emit("changeto", { index, row, type });
    },
    blurIndex(index,row,ind,value,name) {
      this.$emit("blurIndex", { index, row, ind,value,name });
    },
    focusIndex( value ) {
      this.$emit("focusIndex", value );
    },
    changeInp(index, row, type) {
      this.$emit("changeInp", { index, row, type });
    },
    changeTime(row, val) {
      this.$emit("changeTime", { row, val });
    },
    openOsvResult(index, row, type) {
      this.$emit("openOsvResult", { index, row });
    },
    rowClick(val) {
      this.$emit("rowClick", val);
    },
    cellClick(val,column,cell) {
      this.$emit("cellClick", {val,column,cell});
    },

    rowDblclick(val) {
      this.$emit("rowDblclick", val);
    },
    // renderHeader(h,{column}) {
    //   var a = [h,{column}]
    //   this.$emit("renderHeader", a);
    //   return h('div', [
    //     h('span', column.label),
    //     h('i', {
    //         class: 'el-icon-arrow-down',
    //     }),
    //   ]);
    // },

    handleCurrentChange(val) {
      this.$emit("handleCurrentChange", val);
    },
    handleSizeChange(val) {
      this.$emit("handleSizeChange", val);
    },
  },
};
</script>
<style>
.ticketType span {
  margin-right: 10px;
}
#TabelCommon .el-table td,
#TabelCommon .el-table th {
  height: 55px;
}
.el-icon-circle-close {
  color: white;
}
#TabelCommon {
  background: #ffffff;
  height: 100%;
}
</style>