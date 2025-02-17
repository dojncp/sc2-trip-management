<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="行程名称" prop="tripName">
        <el-input
          v-model="queryParams.tripName"
          placeholder="请输入行程名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="移动名称" prop="actName">
        <el-input
          v-model="queryParams.actName"
          placeholder="请输入移动名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="移动序号" prop="actOrder">
        <el-input-number
          v-model="queryParams.actOrder"
          placeholder="请输入移动序号"
          :min="1"
          :step="1"
          controls-position="right"
          clearable
          @keyup.enter="handleQuery"
          style="width: 180px"
        />
      </el-form-item>
<!--      <el-form-item label="移动起点" prop="startPoint">-->
<!--        <el-input-->
<!--          v-model="queryParams.startPoint"-->
<!--          placeholder="请输入移动起点"-->
<!--          clearable-->
<!--          @keyup.enter="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="移动终点" prop="endPoint">-->
<!--        <el-input-->
<!--          v-model="queryParams.endPoint"-->
<!--          placeholder="请输入移动终点"-->
<!--          clearable-->
<!--          @keyup.enter="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="移动方式" prop="actType">-->
<!--        <el-input-->
<!--          v-model="queryParams.actType"-->
<!--          placeholder="请输入移动方式"-->
<!--          clearable-->
<!--          @keyup.enter="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="移动媒介" prop="actTran">-->
<!--        <el-input-->
<!--          v-model="queryParams.actTran"-->
<!--          placeholder="请输入移动媒介"-->
<!--          clearable-->
<!--          @keyup.enter="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="启动时间" prop="actDepTime">-->
<!--        <el-date-picker clearable-->
<!--          v-model="queryParams.actDepTime"-->
<!--          type="date"-->
<!--          value-format="YYYY-MM-DD HH:mm"-->
<!--          placeholder="请选择媒介启动时间"-->
<!--          style="width: 200px">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="停止时间" prop="actArrTime">-->
<!--        <el-date-picker clearable-->
<!--          v-model="queryParams.actArrTime"-->
<!--          type="date"-->
<!--          value-format="YYYY-MM-DD HH:mm"-->
<!--          placeholder="请选择媒介停止时间"-->
<!--          style="width: 200px">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="移动开销" prop="actCost">-->
<!--        <el-input-->
<!--          v-model="queryParams.actCost"-->
<!--          placeholder="请输入移动开销"-->
<!--          clearable-->
<!--          @keyup.enter="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="开销币种" prop="actCostType">-->
<!--        <el-select v-model="queryParams.actCostType" placeholder="请选择开销币种" clearable style="width: 180px">-->
<!--          <el-option-->
<!--            v-for="dict in sc2_trip_budget_currency"-->
<!--            :key="dict.value"-->
<!--            :label="dict.label"-->
<!--            :value="dict.value"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
      <br>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['sc2:acts:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['sc2:acts:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['sc2:acts:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['sc2:acts:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
        v-loading="loading"
        :data="actsList"
        @selection-change="handleSelectionChange"
        style="table-layout: auto; width: 100%"
    >
      <el-table-column type="selection" width="30" align="center" />
<!--      <el-table-column label="移动主键" align="center" prop="actId" />-->
<!--      <el-table-column label="行程" align="center" prop="tripId" width="40" />-->
      <el-table-column label="行程" align="center" prop="tripName" width="100" />
      <el-table-column label="移动名称" align="center" prop="actName" width="80"/>
      <el-table-column label="序号" align="center" prop="actOrder" width="60"/>
      <el-table-column label="起点" align="center" prop="startPoint" width="70"/>
      <el-table-column label="终点" align="center" prop="endPoint" width="70"/>
      <el-table-column label="方式" align="center" prop="actType" width="60"/>
      <el-table-column label="媒介" align="center" prop="actTran" width="60"/>
      <el-table-column label="启动时间" align="center" prop="actDepTime" width="100">
        <template #default="scope">
          <span>{{ parseTime(scope.row.actDepTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="停止时间" align="center" prop="actArrTime" width="100">
        <template #default="scope">
          <span>{{ parseTime(scope.row.actArrTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="等候时间（分钟）" align="center" prop="minuteLastInterval" width="80"/>
      <el-table-column label="开销" align="center" prop="actCost" width="60"/>
      <el-table-column label="币种" align="center" prop="actCostType" width="60">
        <template #default="scope">
          <dict-tag :options="sc2_trip_budget_currency" :value="scope.row.actCostType"/>
        </template>
      </el-table-column>
      <el-table-column
          label="距离（km）"
          align="center"
          prop="actDisKm"
          width="80"
      />
      <el-table-column
          label="速度（km/h）"
          align="center"
          prop="actSpeed"
          width="80"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['sc2:acts:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['sc2:acts:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改移动对话框 -->
    <el-dialog :title="title" v-model="open" width="350px" append-to-body>
      <el-form ref="actsRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="所属行程名称" prop="tripName">
          <el-input v-model="form.tripName" placeholder="请输入所属行程名称" :disabled="isDisabled"/>
        </el-form-item>
        <el-form-item label="移动名称" prop="actName">
          <el-input v-model="form.actName" placeholder="请输入移动名称" />
        </el-form-item>
        <el-form-item label="移动序号" prop="actOrder">
          <el-input-number
              v-model="form.actOrder"
              placeholder="请输入移动序号"
              :min="1"
              :step="1"
              controls-position="right"
              style="width: 100%;"
          />
        </el-form-item>
        <el-form-item label="移动起点" prop="startPoint">
          <el-input v-model="form.startPoint" placeholder="请输入移动起点" />
        </el-form-item>
        <el-form-item label="移动终点" prop="endPoint">
          <el-input v-model="form.endPoint" placeholder="请输入移动终点" />
        </el-form-item>
        <el-form-item label="移动方式" prop="actType">
          <el-input v-model="form.actType" placeholder="请输入移动方式" />
        </el-form-item>
        <el-form-item label="移动媒介" prop="actTran">
          <el-input v-model="form.actTran" placeholder="请输入移动媒介" />
        </el-form-item>
        <el-form-item label="媒介启动时间" prop="actDepTime">
          <el-date-picker clearable
            v-model="form.actDepTime"
            type="datetime"
            value-format="YYYY-MM-DD HH:mm"
            placeholder="请选择媒介启动时间"
            style="width: 250px">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="媒介停止时间" prop="actArrTime">
          <el-date-picker clearable
            v-model="form.actArrTime"
            type="datetime"
            value-format="YYYY-MM-DD HH:mm"
            placeholder="请选择媒介停止时间"
            style="width: 250px">
          </el-date-picker>
        </el-form-item>
<!--        <el-form-item label="等候时间" prop="minuteLastInterval">-->
<!--          <el-input v-model="form.minuteLastInterval" placeholder="请输入等候时间" />-->
<!--        </el-form-item>-->
        <el-form-item label="移动开销" prop="actCost">
          <el-input v-model="form.actCost" placeholder="请输入移动开销" />
        </el-form-item>
        <el-form-item label="开销币种" prop="actCostType">
          <el-select v-model="form.actCostType" placeholder="请选择开销币种">
            <el-option
              v-for="dict in sc2_trip_budget_currency"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="移动距离" prop="actDisKm">
          <el-input v-model="form.actDisKm" placeholder="请输入移动距离" />
        </el-form-item>
<!--        <el-form-item label="移动速度" prop="actSpeed">-->
<!--          <el-input v-model="form.actSpeed" placeholder="请输入移动速度" />-->
<!--        </el-form-item>-->
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Acts">
import { listActs, getActs, delActs, addActs, updateActs} from "@/api/sc2/acts";

const { proxy } = getCurrentInstance();
const { sc2_trip_budget_currency } = proxy.useDict('sc2_trip_budget_currency');

const actsList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const idsName = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const isDisabled = ref();

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    tripId: null,
    tripName: null,
    actName: null,
    actOrder: null,
    startPoint: null,
    endPoint: null,
    actType: null,
    actTran: null,
    actDepTime: null,
    actArrTime: null,
    actCost: null,
    actCostType: null,
  },
  rules: {
    tripName: [
      { required: true, message: "行程名称不能为空", trigger: "blur" }
    ],
    actOrder: [
      { required: true, message: "移动序号不能为空", trigger: "blur" }
    ],
    actName: [
      { required: true, message: "移动名称不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询移动列表 */
function getList() {
  loading.value = true;
  // 已经增加了tripName一项
  listActs(queryParams.value).then(response => {
    actsList.value = response.rows;
    total.value = response.total;
    loading.value = false;
    // console.log(actsList.value)
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    actId: null,
    tripId: null,
    tripName: null,
    actName: null,
    actOrder: null,
    startPoint: null,
    endPoint: null,
    actType: null,
    actTran: null,
    actDepTime: null,
    actArrTime: null,
    minuteLastInterval: null,
    actCost: null,
    actCostType: null,
    actDisKm: null,
    actSpeed: null
  };
  proxy.resetForm("actsRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.actId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加移动";
  isDisabled.value = false; // 新增状态下可编辑行程名称
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _actId = row.actId || ids.value
  getActs(_actId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改移动";
  });
  isDisabled.value = true; // 修改状态下不可编辑行程名称
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["actsRef"].validate(valid => {
    if (valid) {
      if (form.value.actId != null) {
        // console.log(form.value);
        updateActs(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addActs(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _actIds = row.actId || ids.value;
  const _actName = row.actName || idsName.value;
  // proxy.$modal.confirm('是否确认删除移动编号为"' + _actIds + '"的数据项？').then(function() {
  proxy.$modal.confirm('是否确认删除移动名称为" ' + _actName + ' "的数据项？').then(function() {
    return delActs(_actIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('sc2/acts/export', {
    ...queryParams.value
  }, `acts_${new Date().getTime()}.xlsx`)
}

getList();
</script>

<style>
.el-picker-panel__body {
  display: flex;
  flex-direction: column-reverse; /* 反转排列顺序 */
}
</style>
