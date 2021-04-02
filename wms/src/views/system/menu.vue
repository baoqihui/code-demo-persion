<template>
  <div class="app-container"> 

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col> 
    </el-row>

    <el-table
      v-loading="loading"
      :data="menuList"
      row-key="id"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column prop="perName" label="菜单名称" :show-overflow-tooltip="true" width="200"></el-table-column>
      <!-- <el-table-column prop="icon" label="图标" align="center" width="150">
        <template slot-scope="scope">
          <svg-icon :icon-class="scope.row.icon" />
          <i :class="scope.row.icon"></i>
        </template>
      </el-table-column> -->
      <!-- <el-table-column prop="perSort" label="排序" width="60"></el-table-column> -->
      <el-table-column prop="path" label="路由地址" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="component" label="组件路径" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="status" label="状态" :formatter="statusFormat" width="80"></el-table-column>
      <el-table-column prop="perType" label="类型" :formatter="perTypeFormat" width="80"></el-table-column>
      <!-- <el-table-column label="创建时间" align="center" prop="createTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" 
            type="text" 
            icon="el-icon-edit" 
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button 
            v-if="scope.row.perType==0"
            size="mini" 
            type="text" 
            icon="el-icon-plus" 
            @click="handleAdd(scope.row)"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改菜单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="上级菜单">
              <treeselect
                v-model="form.parentId"
                :options="menuOptions"
                :normalizer="normalizer"
                :show-count="true"
                placeholder="选择上级菜单"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="菜单类型" prop="perType">
              <el-radio-group v-model="form.perType"> 
                <el-radio :label="0">菜单</el-radio>
                <el-radio :label="1">按钮</el-radio>
              </el-radio-group>
            </el-form-item> 
          </el-col>
          <!-- <el-col :span="24">
            <el-form-item v-if="form.perType != 'F'" label="菜单图标">
              <el-popover
                placement="bottom-start"
                width="460"
                trigger="click"
                @show="$refs['iconSelect'].reset()"
              >
                <IconSelect ref="iconSelect" @selected="selected" />
                <el-input slot="reference" v-model="form.icon" placeholder="点击选择图标" readonly>
                  <svg-icon
                    v-if="form.icon"
                    slot="prefix"
                    :icon-class="form.icon"
                    class="el-input__icon"
                    style="height: 32px;width: 16px;"
                  />
                  <i v-else slot="prefix" class="el-icon-search el-input__icon" />
                </el-input>
              </el-popover>
            </el-form-item>
          </el-col> -->
          <el-col :span="12">
            <el-form-item label="菜单名称" prop="perName">
              <el-input v-model="form.perName" placeholder="请输入菜单名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示排序" prop="perSort">
              <el-input-number v-model="form.perSort" controls-position="right" :min="0" />
            </el-form-item>
          </el-col> 
          <el-col :span="12">
            <el-form-item  label="路由地址" prop="path">
              <el-input v-model="form.path" placeholder="请输入路由地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.perType == '0'">
            <el-form-item label="组件路径" prop="component">
              <el-input v-model="form.component" placeholder="请输入组件路径" />
            </el-form-item>
          </el-col> 
          <el-col :span="12">
            <el-form-item v-if="form.perType == '0'" label="菜单状态">
              <el-radio-group v-model="form.status">
                <el-radio :label="true">启用</el-radio>
                <el-radio :label="false">禁用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col> 
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script> 
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import IconSelect from "@/components/IconSelect";

export default {
  name: "Menu",
  components: { Treeselect, IconSelect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 菜单表格树数据
      menuList: [],
      // 菜单树选项
      menuOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 显示状态数据字典
      visibleOptions: [],
      // 菜单状态数据字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        menuName: undefined,
        visible: undefined
      },
      // 表单参数
      form: {
      },
      // 表单校验
      rules: {
        perName: [
          { required: true, message: "菜单名称不能为空", trigger: "blur" }
        ],
        // perSort: [
        //   { required: true, message: "菜单顺序不能为空", trigger: "blur" }
        // ],
        path: [
          { required: true, message: "路由地址不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList(); 
  },
  methods: {
    // 选择图标
    selected(name) {
      this.form.icon = name;
    },
    /** 查询菜单列表 */
    getList() {  
        this.api({
            url:'/ums/umsPermission/selectPermissionTreeByParentId/0',
            method:'post', 
        }).then(res=>{
            this.loading = false;
            this.menuList = this.handleTree(res.datas, "id"); 
        }) 
    },
    /** 转换菜单数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.perName,
        children: node.children
      };
    },
    /** 查询菜单下拉树结构 */
    getTreeselect() {
        this.api({
            url:'/ums/umsPermission/selectPermissionTreeByParentId/0',
            method:'post', 
        }).then(res=>{
            this.menuOptions = []; 
            const menu = { id: 0, perName: '主类目', children: [] };
            menu.children = this.handleTree(res.datas, "id");
            this.menuOptions.push(menu);
        }) 
    },
    // 显示状态字典翻译
     
    // 菜单状态字典翻译
    statusFormat(row, column) {
      if (row.status) {
        return "正常";
      }else{
        return '禁止'
      }
    },
    // 类型字典翻译
    perTypeFormat(row, column) {
      if (row.perType==0) {
        return "菜单";
      }else{
        return '按钮'
      }
    },
     
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        parentId: 0,
        perName: undefined,
        icon: undefined,
        perType: 0, 
        perSort: undefined,  
        status: true,
        // path:undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {   
      this.reset();
      this.getTreeselect();
      if (row != null && row.id) {
        this.form.parentId = row.id;
      } else {
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "添加菜单";  
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect(); 
        this.form = JSON.parse(JSON.stringify(row));
        this.open = true;
        this.title = "修改菜单";
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            this.api({
                url:'/ums/umsPermission/save',
                method:'post',
                data:this.form
            }).then(res=>{
                if (res.resp_code === 0) {
                this.winmeg(res.resp_msg);
                this.open = false;
                this.getList();
              }
            }) 
          } else {
            this.api({
                url:'/ums/umsPermission/save',
                method:'post',
                data:this.form
            }).then(res=>{
                if (res.resp_code === 0) {
                this.winmeg(res.resp_msg);
                this.open = false;
                this.getList();
              }
            }) 
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除名称为"' + row.perName + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(()=> {
          this.api({
            url:'/ums/umsPermission/del/' + row.id,
            method:'post',
          }).then(res=>{
            this.getList();
            this.winmeg("删除成功");
          })
        }) 
    }
  }
};
</script>