<template>
  <a-modal
    title="编辑元数据"
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
          style="display: none;"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['id']" />
        </a-form-item>

        <a-form-item
          style="display:none;"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input v-decorator="['tableCode']" />
        </a-form-item>

        <a-form-item
          label="元数据表"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input :disabled="true" v-decorator="['tableName']" />
        </a-form-item>

        <a-form-item
          v-if="addChildren"
          label="上级"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input  :disabled= "true " v-decorator="['parentCode']" />
        </a-form-item>
        <a-form-item
          label="唯一编码"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input :disabled="true" placeholder="请输入唯一编码" v-decorator="['code', {rules: [{required: true, message: '请输入唯一编码！'}]}]" />
        </a-form-item>
        <a-form-item
          label="元数据名称"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入元数据名称" v-decorator="['name', {rules: [{required: true, message: '请输入元数据名称！'}]}]" />
        </a-form-item>
        <a-form-item
          label="数据类型"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-select style="width: 100%" placeholder="请选择数据类型" v-decorator="['dataTypeCode', {rules: [{ required: true, message: '请选择数据类型！' }]}]" >
            <a-select-option v-for="(item,index) in dataTypeList" :key="index" :value="item.code" @click="changeDataType(item.code)">{{ item.value }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="字段编号"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
          v-if="showMarcField"
        >
          <a-input placeholder="请输入字段编号" v-decorator="['marcField', {rules: [{required: true, message: '请输入字段编号！'}]}]" />
        </a-form-item>
        <a-form-item
          label="子字段编号"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
          v-if="showMarcField"
        >
          <a-input placeholder="请输入子字段编号" v-decorator="['marcChildrenField', {rules: [{required: true, message: '请输入子字段编号！'}]}]" />
        </a-form-item>
        <a-form-item
          label="字段指示符"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
          v-if="showMarcField"
        >
          <a-input placeholder="请输入字段指示符" v-decorator="['marcIndicator']" />
        </a-form-item>
        <a-form-item
          label="字段下标"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
          v-if="showExcelField"
        >
          <a-input placeholder="请输入字段下标" v-decorator="['excelFieldIndex', {rules: [{required: true, message: '请输入字段下标！'}]}]" />
        </a-form-item>
        <a-form-item
          label="AS字段名"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
          v-if="showASField"
        >
          <a-input placeholder="请输入字段名称" v-decorator="['asFieldName', {rules: [{required: true, message: '请输入字段名称！'}]}]" />
        </a-form-item>
        <a-form-item
          label="ES字段名"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
          v-if="showESField"
        >
          <a-input placeholder="请输入字段名称" v-decorator="['esFieldName', {rules: [{required: true, message: '请输入字段名称！'}]}]" />
        </a-form-item>
        <a-form-item
          label="分词器"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
          v-if="showESField"
        >
          <a-select style="width: 100%" placeholder="请选择分词器" v-decorator="['esFieldAnalyzer', {rules: [{ required: true, message: '请选择分词器！' }]}]" >
            <a-select-option v-for="(item,index) in esAnalyzerList" :key="index" :value="item" >{{ item }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="长度"
        >
          <a-input-number placeholder="请输入字段长度" style="width: 100%" v-decorator="['length', {rules: [{ required: true, message: '请输入字段长度！' }]}]" :min="1" :max="1000" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="排序"
        >
          <a-input-number placeholder="请输入排序" style="width: 100%" v-decorator="['sort', { initialValue: 100 }]" :min="1" :max="1000" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否创建索引"
          v-if="showESField"
        >
          <a-switch id="esFieldIndex" checkedChildren="是" unCheckedChildren="否" v-decorator="['esFieldIndex', { valuePropName: 'checked' }]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否非空"
        >
          <a-switch id="isNull" checkedChildren="是" unCheckedChildren="否" v-decorator="['isNull', { valuePropName: 'checked' }]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否允许多列"
        >
          <a-switch id="moreColumn" checkedChildren="是" unCheckedChildren="否" v-decorator="['moreColumn', { valuePropName: 'checked' }]"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import { sysDictDataChildren } from '@/api/modular/system/dictDataManage'
  import { metadataColumnChildrenList, metadataColumnEdit } from '@/api/modular/main/metadataColumn'
  import { mapGetters, mapState } from 'vuex'
  export default {
    computed: {
      ...mapGetters({
        getExcelMetadataCode: 'constant/getExcelMetadataCode',
        getMarcMetadataCode: 'constant/getMarcMetadataCode',
        getASMetadataCode: 'constant/getASMetadataCode',
        getESMetadataCode: 'constant/getESMetadataCode'
      }),
      ...mapState({
        esAnalyzerList: state => state.constant.esAnalyzerList
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
        visibleDef: false,
        form: this.$form.createForm(this),
        addChildren: false,
        metadataTypeList: [],
        dataTypeList: [],
        statusList: [],
        // 元数据类型
        typeCode: '',
        // 是否显示marc数据字段
        showMarcField: false,
        // 是否显示excel数据字段
        showExcelField: false,
        // 是否显示AS数据字段
        showASField: false,
        // 是否显示ES数据字段
        showESField: false,
        // 元数据  数据类型
        dataTypeCode: ''
      }
    },
    methods: {
      // 初始化方法
      edit (record) {
        this.dataTypeCode = record.scope.dataTypeCode
        this.typeCode = record.typeCode
        if (this.typeCode === this.getASMetadataCode()) {
          this.showASField = true
        } else if (this.typeCode === this.getESMetadataCode()) {
          this.showESField = true
        } else {
          this.showASField = false
          this.showESField = false
        }
        this.changeDataType(record.scope.dataTypeCode)
        if (record.parentId !== undefined && record.parentId !== '0') {
          this.addChildren = true
        } else {
          this.addChildren = false
        }
        this.visible = true
        setTimeout(() => {
          this.form.setFieldsValue(
            {
              id: record.scope.id,
              parentCode: record.scope.parentCode === undefined ? '' : record.scope.parentCode,
              code: record.scope.code,
              name: record.scope.name,
              dataTypeCode: record.scope.dataTypeCode,
              sort: record.scope.sort,
              length: record.scope.length,
              moreColumn: record.scope.moreColumn === 1,
              isNull: record.scope.isNull === 1,
              marcField: record.scope.marcField,
              marcChildrenField: record.scope.marcChildrenField,
              marcIndicator: record.scope.marcIndicator,
              excelStartRow: record.scope.excelStartRow,
              excelFieldIndex: record.scope.excelFieldIndex,
              asFieldName: record.scope.asFieldName,
              esFieldName: record.scope.esFieldName,
              esFieldIndex: record.scope.esFieldIndex === 1,
              esFieldAnalyzer: record.scope.esFieldAnalyzer === null ? this.esAnalyzerList[0] : record.scope.esFieldAnalyzer,
              tableCode: record.tableCode,
              tableName: record.tableName

            }
          )
        }, 100)
        this.initDataType(record.typeCode)
      },
      initDataType(typeCode) {
        sysDictDataChildren({ typeId: 0, code: typeCode }).then((res) => {
          this.dataTypeList = []
          res.data.forEach((row) => {
            this.dataTypeList.push({ id: row.id, code: row.code, value: row.value })
          })
        })
      },
      handleSubmit () {
        const { form: { validateFields } } = this
        this.confirmLoading = true
        validateFields((errors, values) => {
          values.moreColumn = values.moreColumn ? 1 : 0
          values.isNull = values.isNull ? 1 : 0
          values.esFieldIndex = values.esFieldIndex ? 1 : 0
          if (!errors) {
            const dataType = this.dataTypeCode.split('_')
            if (dataType.length > 1 && dataType[1] === 'json' && this.dataTypeCode !== values.dataTypeCode) {
              metadataColumnChildrenList({ parentCode: values.code }).then((res) => {
                if (res.data !== null && res.data.length > 0) {
                  this.$message.warning('无法把json类型的数据修改成其他类型，因为下面有子数据！')
                  this.confirmLoading = false
                } else {
                  this.update(values)
                }
              })
            } else {
              this.update(values)
            }
          } else {
            this.confirmLoading = false
          }
        })
      },
      update(values) {
        metadataColumnEdit(values).then((res) => {
          if (res.success) {
            this.$message.success('编辑成功')
            this.visible = false
            this.confirmLoading = false
            this.$emit('ok', values)
          } else {
            this.$message.error('编辑失败：' + res.message)
          }
        }).finally((res) => {
          this.confirmLoading = false
        })
      },
      handleCancel () {
        this.form.resetFields()
        this.visible = false
      },
      changeDataType(code) {
        const dataType = code.split('_')
        if (dataType[dataType.length - 1] !== 'json') {
          if (this.typeCode === this.getExcelMetadataCode()) {
            this.showExcelField = true
          } else if (this.typeCode === this.getMarcMetadataCode()) {
            this.showMarcField = true
          }
        } else {
          this.showMarcField = false
          this.showExcelField = false
        }
      }
    }
  }
</script>
