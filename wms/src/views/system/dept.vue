<template>
  <div class="app-container">
    <!-- <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
      <el-form-item label="部门名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入部门名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="部门状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form> -->

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <!-- <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="deptList"
      row-key="id"
      default-expand-all
      :tree-props="{children: 'childVOS', hasChildren: 'hasChildren'}"
    >
      <el-table-column prop="name" label="部门名称"></el-table-column>
      <el-table-column prop="code" label="部门代码"></el-table-column>
      <!-- <el-table-column prop="status" label="状态" :formatter="statusFormat" width="100"></el-table-column> -->
      <!-- <el-table-column label="创建时间" align="center" prop="createTime" width="200">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button 
            size="mini" 
            type="text" 
            icon="el-icon-edit" 
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button 
            size="mini" 
            type="text" 
            icon="el-icon-plus" 
            @click="handleAdd(scope.row)"
          >新增</el-button>
          <el-button
            v-if="scope.row.parentCode != 0"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改部门对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24" >
            <el-form-item label="上级部门" prop="parentCode" v-if="!((form.parentCode=='0')&&(form.code='HENA'))">
              <treeselect v-model="form.parentCode" :options="deptOptions" :normalizer="normalizer" placeholder="选择上级部门" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部门名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入部门名称" />
            </el-form-item>
          </el-col> 
          <el-col :span="12">
            <el-form-item label="部门代码" prop="code">
              <el-input v-model="form.code" placeholder="请输入部门名称" />
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
// import { listDept, getDept, delDept, addDept, updateDept, listDeptExcludeChild } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Dept",
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 表格树数据
      deptList: [],
      // 部门树选项
      deptOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态数据字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        deptName: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        parentCode: [
          { required: true, message: "上级部门不能为空", trigger: "blur" }
        ],
        deptName: [
          { required: true, message: "部门名称不能为空", trigger: "blur" }
        ],
        orderNum: [
          { required: true, message: "菜单顺序不能为空", trigger: "blur" }
        ],
        email: [
          {
            type: "email",
            message: "'请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        phone: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ]
      }
    };
  },
  created() {
    this.getList();
    // this.getDicts("sys_normal_disable").then(response => {
    //   this.statusOptions = response.data;
    // });
  },
  methods: {
    // /** 查询部门列表 */
    // getList() {
    //   this.loading = true;
    //   listDept(this.queryParams).then(response => {
    //     this.deptList = this.handleTree(response.data, "deptId");
    //     this.loading = false;
    //   });
    // },
    //   查询部门
    getList() {
        this.loading = true;
      this.api({
        url:"/ums/umsDepa/depaTree/0",
        method:'post'
      }).then((res) => {
          this.loading = false;
        if (res.resp_code == 0) {
          this.deptList = this.handleTree( res.datas, "id"); 
        }
      });
    }, 
    /** 转换部门数据结构 */
    normalizer(node) {
      if (node.childVOS && !node.childVOS.length) {
        delete node.childVOS;
      }
      return {
        id: node.code,
        label: node.name,
        children: node.childVOS
      };
    },
    // 字典状态字典翻译
    statusFormat(row, column) {
    //   return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        code: undefined,
        parentCode: undefined,
        name: undefined, 
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
      if (row != undefined) {
        this.form.parentCode = row.code;
      }
      this.open = true;
      this.title = "添加部门";
      this.api({
        url:"/ums/umsDepa/depaTree/0",
        method:'post'
      }).then((res) => { 
        if (res.resp_code == 0) {
          this.deptOptions = this.handleTree( res.datas, "code"); 
        }
      }); 
    },
    /** 修改按钮操作 */
    handleUpdate(row) { 
      this.reset();
      this.open = true;
      this.form = JSON.parse(JSON.stringify(row)) 
      this.api({
        url:"/ums/umsDepa/depaTree/0",
        method:'post'
      }).then((res) => { 
        if (res.resp_code == 0) {
          this.deptOptions = this.handleTree( res.datas, "code"); 
        }
      });  
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) { 
            this.api({
                url:'/ums/umsDepa/save',
                method:'post',
                data:this.form
            }).then(response => { 
                this.open = false;
                this.winmeg(response.resp_msg)
                this.getList(); 
            }); 
            // this.api({
            //     url:'/ums/umsDepa/save',
            //     method:'post',
            //     data:this.form
            // }).then(response => { 
            //     this.msgSuccess("新增成功");
            //     this.open = false;
            //     this.getList(); 
            // }); 
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除名称为"' + row.deptName + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(()=> {
          this.api({
            url:'/ums/umsDepa/del/' + row.id,
            method : 'post'
          }).then((res) => {
            this.winmeg(res.resp_msg)
            this.getList(); 
          })
        }) 
    }
  }
};
</script>