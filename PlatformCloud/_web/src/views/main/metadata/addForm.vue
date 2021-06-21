<template>
  <a-modal
    title="新增元数据表"
    :width="900"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
    :maskClosable="false"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          label="元数据类型"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-select style="width: 100%" placeholder="请选择元数据类型" v-decorator="['typeCode', {rules: [{ required: true, message: '请选择元数据类型！' }]}]" >
            <a-select-option v-for="(item,index) in metadataTypeList" :key="index" :value="item.code" @click="changeMetadataType(item)">{{ item.value }}</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item
          label="唯一编码"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入唯一编码" v-decorator="['code', {rules: [{required: true, message: '请输入唯一编码！'}]}]" />
        </a-form-item>

        <a-form-item
          label="名称"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入名称" v-decorator="['name', {rules: [{required: true, message: '请输入名称！'}]}]" />
        </a-form-item>

        <a-form-item
          label="api路径"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
          v-if="showASField"
        >
          <a-input  placeholder="请输入api路径" style="width: 100%" v-decorator="['dataSourceTableName', {rules: [{required: true, message: '请输入api路径！'}]}]" />
        </a-form-item>

        <a-form-item
          label="sheet名称"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
          v-if="showExcelField"
        >
          <a-input  placeholder="请输入sheet名称" style="width: 100%" v-decorator="['dataSourceTableName', {rules: [{required: true, message: '请输入sheet名称！'}]}]" />
        </a-form-item>

        <a-form-item
          label="数据起始行"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
          v-if="showExcelField"
        >
          <a-input-number placeholder="请输入数据起始行" style="width: 100%" v-decorator="['excelStartRow', {rules: [{required: true, message: '请输入数据起始行！'}]}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="排序"
        >
          <a-input-number placeholder="请输入排序" style="width: 100%" v-decorator="['sort', { initialValue: 100 }]" :min="1" :max="1000" />
        </a-form-item>

        <a-form-item
          label="备注"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-textarea :rows="4" placeholder="请输入备注" v-decorator="['remark']"></a-textarea>
        </a-form-item>

      </a-form>

    </a-spin>
  </a-modal>
</template>

<script>
  import { sysDictTypeDropDown } from '@/api/modular/system/dictManage'
  import { metadataTableAdd } from '@/api/modular/main/metadataTable'
  import { sysDictDataChildren } from '@/api/modular/system/dictDataManage'
  import { mapGetters } from 'vuex'
  export default {
    computed: {
      ...mapGetters({
        getExcelMetadataCode: 'constant/getExcelMetadataCode',
        getASMetadataCode: 'constant/getASMetadataCode'
      })
    },
    data () {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 15 }
        },
        visible: false,
        confirmLoading: false,
        form: this.$form.createForm(this),
        metadataTypeList: [],
        // 是否显示excel数据字段
        showExcelField: false,
        // 是否显示AS数据字段
        showASField: false
      }
    },
    methods: {
      // 初始化方法
      add (record) {
        this.showExcelField = false
        this.showASField = false
        this.visible = true
        this.sysDictTypeDropDown()
      },

      handleSubmit () {
        const { form: { validateFields } } = this
        this.confirmLoading = true
        validateFields((errors, values) => {
          if (!errors) {
            metadataTableAdd(values).then((res) => {
              if (res.success) {
                this.$message.success('新增成功')
                this.visible = false
                this.confirmLoading = false
                this.$emit('ok', values)
                this.form.resetFields()
              } else {
                this.$message.error('新增失败：' + res.message)
              }
            }).finally((res) => {
              this.confirmLoading = false
            })
          } else {
            this.confirmLoading = false
          }
        })
      },
      sysDictTypeDropDown () {
        sysDictTypeDropDown({ code: 'metadata_type' }).then((res) => {
          this.metadataTypeList = []
          res.data.forEach(item => {
            if (item.parentId === null || item.parentId === '0') {
              this.metadataTypeList.push(item)
            }
          })
        })
      },
      handleCancel () {
        this.form.resetFields()
        this.visible = false
      },
      changeMetadataType(item) {
        this.dataTypeList = []
        sysDictDataChildren({ typeId: 0, parentId: item.id }).then((res) => {
          res.data.forEach((row) => {
            this.dataTypeList.push({ id: row.id, code: row.code, value: row.value })
          })
        })
        if (item.code === this.getExcelMetadataCode()) {
          this.showExcelField = true
        } else {
          this.showExcelField = false
        }
        if (item.code === this.getASMetadataCode()) {
          this.showASField = true
        } else {
          this.showASField = false
        }
      }
    }
  }
</script>
