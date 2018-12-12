import React, {PureComponent, Fragment} from 'react';
import {formatMessage, FormattedMessage} from 'umi/locale';
import {Card, Table, Icon, Switch, Badge, Divider, Button, Dropdown, Menu, Form, Modal, Input, InputNumber} from 'antd';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import {connect} from 'dva';
import styles from "./User.less";

const FormItem = Form.Item;

@Form.create()
class CreateForm extends PureComponent {

  okHandle = () => {
    const {form, handleSave} = this.props;
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      form.resetFields();
      handleSave(fieldsValue);
    });
  };

  render() {
    const {modalVisible, form, action, handleModalVisible, formVal} = this.props;
    const title = action === "save" ? "新建权限" : "更新权限";
    return (
      <Modal
        destroyOnClose
        title={title}
        visible={modalVisible}
        onOk={this.okHandle}
        onCancel={() => handleModalVisible()}
      >
        <FormItem>
          {form.getFieldDecorator('id', {initialValue: formVal.id})(<Input type="hidden"/>)}
        </FormItem>
        <FormItem labelCol={{span: 5}} wrapperCol={{span: 15}} label="编码">
          {form.getFieldDecorator('code', {
            initialValue: formVal.code,
          })(<Input placeholder="请输入"/>)}
        </FormItem>
        <FormItem labelCol={{span: 5}} wrapperCol={{span: 15}} label="父编码">
          {form.getFieldDecorator('parentCode', {
            initialValue: formVal.parentCode,
          })(<Input placeholder="请输入" disabled/>)}
        </FormItem>
        <FormItem labelCol={{span: 5}} wrapperCol={{span: 15}} label="名称">
          {form.getFieldDecorator('name', {
            initialValue: formVal.name,
          })(<Input placeholder="请输入"/>)}
        </FormItem>
        <FormItem labelCol={{span: 5}} wrapperCol={{span: 15}} label="图标">
          {form.getFieldDecorator('icon', {
            initialValue: formVal.icon,
          })(<Input placeholder="请输入"/>)}
        </FormItem>
        <FormItem labelCol={{span: 5}} wrapperCol={{span: 15}} label="url">
          {form.getFieldDecorator('url', {
            initialValue: formVal.url,
          })(<Input placeholder="请输入"/>)}
        </FormItem>
        <FormItem labelCol={{span: 5}} wrapperCol={{span: 15}} label="组件">
          {form.getFieldDecorator('component', {
            initialValue: formVal.component,
          })(<Input placeholder="请输入"/>)}
        </FormItem>
        <FormItem labelCol={{span: 5}} wrapperCol={{span: 15}} label="排序">
          {form.getFieldDecorator('priority', {
            initialValue: formVal.priority,
          })(<InputNumber placeholder="请输入"/>)}
        </FormItem>
        <FormItem labelCol={{span: 5}} wrapperCol={{span: 15}} label="层级">
          {form.getFieldDecorator('level', {
            initialValue: formVal.level,
          })(<InputNumber placeholder="请输入" disabled/>)}
        </FormItem>
        <FormItem labelCol={{span: 5}} wrapperCol={{span: 15}} label="是否为菜单">
          {form.getFieldDecorator('isMenu', {
            initialValue: formVal.isMenu,
          })(<Switch checkedChildren="是" unCheckedChildren="否" defaultChecked={formVal.isMenu === 1}/>)}
        </FormItem>
        <FormItem labelCol={{span: 5}} wrapperCol={{span: 15}} label="备注">
          {form.getFieldDecorator('tips', {
            initialValue: formVal.tips,
          })(<Input placeholder="请输入"/>)}
        </FormItem>
      </Modal>
    );
  }
}

@connect(({permission, loading}) => ({
  permission,
  loading: loading.models.permission,
}))
export default class Permission extends PureComponent {

  state = {
    selectedRows: [],
    modalVisible: false,
    action: "save",
    formVal: {},
  };

  columns = [{
    title: 'ID',
    dataIndex: 'id',
    key: 'id',
    width: '12%',
  }, {
    title: '编码',
    dataIndex: 'code',
    key: 'code',
  }, {
    title: '父编码',
    dataIndex: 'parentCode',
    key: 'parentCode',
  }, {
    title: '名称',
    dataIndex: 'name',
    key: 'name',
  }, {
    title: '图标',
    dataIndex: 'icon',
    key: 'icon',
    render: function (text, record, index) {
      return (
        <Icon type={text}/>
      );
    }
  }, {
    title: '路径',
    dataIndex: 'url',
    key: 'url',
  }, {
    title: '层级',
    dataIndex: 'level',
    key: 'level',
    render: function (text, record, index) {
      const color = text === 1 ? '#52c41a' : '#faad14';
      return <Badge count={text} style={{backgroundColor: color}}/>;
    }
  }, {
    title: '排序',
    dataIndex: 'priority',
    key: 'priority',
    render: function (text, record, index) {
      return <Badge count={text} style={{backgroundColor: '#52c41a'}}/>;
    }
  }, {
    title: '是菜单',
    dataIndex: 'isMenu',
    key: 'isMenu',
    render: function (text, record, index) {
      return <Switch checkedChildren="是" unCheckedChildren="否" defaultChecked={text === 1} disabled/>;
    }
  }, {
    title: '操作',
    render: (text, record) => (
      <Fragment>
        <a onClick={() => this.handleModalVisible(true, 'update', record)}>编辑</a>
        <Divider type="vertical"/>
        <a onClick={() => this.handleModalVisible(true, 'save', record)}>添加权限</a>
      </Fragment>
    ),
  },];

  rowSelection = {
    onChange: (selectedRowKeys, selectedRows) => {
      console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
    },
    getCheckboxProps: record => ({
      disabled: record.name === 'Disabled User', // Column configuration not to be checked
      name: record.name,
    }),
  };

  componentDidMount() {
    this.props.dispatch({
      type: 'permission/fetch',
    });
  }

  handleModalVisible = (flag, action, record) => {
    // const {selectedRows} = this.state;
    if (action === "update") {
      this.setState({formVal: record});
    }
    this.setState({
      modalVisible: !!flag,
      action: action ? action : "save",
    });
  };

  handleSave = fields => {
    const {dispatch} = this.props;
    const {currentPage, pageSize, action} = this.state;
    dispatch({
      type: 'permission/savePermission',
      payload: {
        ...fields,
        currentPage,
        pageSize,
      },
    });

    message.success('添加成功');
    this.handleModalVisible();
  };

  render() {
    const {permission} = this.props;
    const {modalVisible, action, formVal, selectedRows} = this.state;
    const parentMethods = {
      handleSave: this.handleSave,
      handleModalVisible: this.handleModalVisible,
    };
    const defaultProps = {
      modalVisible,
      action,
      formVal,
    };
    const menu = (
      <Menu onClick={this.handleMenuClick} selectedKeys={[]}>
        <Menu.Item key="remove">删除</Menu.Item>
        {/*<Menu.Item key="approval">批量审批</Menu.Item>*/}
      </Menu>
    );
    return (
      <PageHeaderWrapper>
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListOperator}>
              {/*<Button icon="plus" type="primary" onClick={() => this.handleModalVisible(true, "save")}>
                新建
              </Button>*/}
              {/*<Button icon="edit" type="primary" onClick={() => this.handleModalVisible(true, "update")}>
                更新
              </Button>*/}
              {selectedRows.length > 0 && (
                <span>
                  {/*<Button>批量操作</Button>*/}
                  <Dropdown overlay={menu}>
                    <Button>
                      更多操作 <Icon type="down"/>
                    </Button>
                  </Dropdown>
                </span>
              )}
            </div>
          </div>
          <Table columns={this.columns} dataSource={permission} bordered={false} rowSelection={this.rowSelection}/>
          <CreateForm {...parentMethods} {...defaultProps}/>
        </Card>
      </PageHeaderWrapper>
    );
  }
};

