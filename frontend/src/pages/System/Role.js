import React, {PureComponent} from 'react';
import {formatMessage, FormattedMessage} from 'umi/locale';
import {Button, Card, Dropdown, Form, Icon, Input, Menu, message, Modal, Tree} from 'antd';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import StandardTable from "@/components/StandardTable";
import {connect} from 'dva';
import styles from "./User.less";

const FormItem = Form.Item;
const {TreeNode} = Tree;

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
    const {modalVisible, form, action, handleModalVisible, values: formVal} = this.props;
    const title = action === "save" ? "新建角色" : "更新角色";
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
        <FormItem labelCol={{span: 5}} wrapperCol={{span: 15}} label="角色名">
          {form.getFieldDecorator('roleName', {
            rules: [{required: true, message: '请输入至少两个字符的角色名！ ', min: 2}],
            initialValue: formVal.userName,
          })(<Input placeholder="请输入"/>)}
        </FormItem>
      </Modal>
    );
  }
}

class PermissionWin extends PureComponent {

  state = {
    checkedKeys: [],
  };

  okHandle = () => {
    const {handleSavePermission, roleId} = this.props;
    const {checkedKeys} = this.state;
    handleSavePermission(roleId, checkedKeys);
  };

  renderTreeNodes = (data = []) => data.map((item) => {
    const title = `${item.name}(${item.url})`;
    if (item.children) {
      return (
        <TreeNode title={title} key={item.id} dataRef={item}>
          {this.renderTreeNodes(item.children)}
        </TreeNode>
      );
    }
    return <TreeNode title={title} key={item.id} dataRef={item}/>;
  });

  onCheck = (checkedKeys, e) => {
    this.setState({
      checkedKeys: checkedKeys,
    });
  };

  render() {
    const {permissionWinVisible, handlePermissionWinVisible, allPermissions, permissionIds} = this.props;
    return (
      <Modal
        destroyOnClose
        title={"设置权限"}
        visible={permissionWinVisible}
        onOk={this.okHandle}
        onCancel={() => handlePermissionWinVisible()}
      >
        <Tree
          checkable={true}
          // onExpand={this.onExpand}
          // expandedKeys={this.state.expandedKeys}
          // autoExpandParent={this.state.autoExpandParent}
          onCheck={this.onCheck}
          checkedKeys={permissionIds}
          // onSelect={this.onSelect}
          // selectedKeys={this.state.selectedKeys}
        >
          {this.renderTreeNodes(allPermissions)}
        </Tree>
      </Modal>
    );
  }
}

@connect(({role, loading}) => ({
  role,
  loading: loading.models.role,
}))
export default class Role extends PureComponent {

  state = {
    selectedRows: [],
    modalVisible: false,
    currentPage: 1,
    pageSize: 10,
    action: "save",//save.update
    formVal: {},
    permissionWinVisible: false,
    allPermissions: [],
    roleId: null,
  };

  columns = [{
    title: 'ID',
    dataIndex: 'id',
    key: 'id',
    width: '12%',
  }, {
    title: '角色名',
    dataIndex: 'roleName',
    key: 'roleName',
  }];

  handleModalVisible = (flag, action) => {
    const {selectedRows} = this.state;
    if (action === "update") {
      if (selectedRows.length !== 1) {
        message.warn("请选择且最多一条记录！");
        return;
      } else {
        if (selectedRows.length > 0) {
          this.setState({formVal: selectedRows[0]});
        }
      }
    }
    this.setState({
      modalVisible: !!flag,
      action: action ? action : "save",
    });
  };

  handlePermissionWinVisible = (flag) => {
    const {selectedRows} = this.state;
    const {dispatch} = this.props;
    if (selectedRows.length !== 1) {
      message.warn("请选择且最多一条记录！");
      return;
    }
    dispatch({
      type: 'role/getRolePermission',
      payload: {
        id: selectedRows[0].id,
      }
    });
    this.setState({
      permissionWinVisible: !!flag,
      roleId: selectedRows[0].id,
    });
  };

  handleUpdateModalVisible = (flag, record) => {
    this.setState({
      updateModalVisible: !!flag,
      stepFormValues: record || {},
    });
  };

  handleSave = fields => {
    const {dispatch} = this.props;
    const {currentPage, pageSize, action} = this.state;
    dispatch({
      type: 'role/saveRole',
      payload: {
        ...fields,
        currentPage,
        pageSize,
      },
    });

    message.success('添加成功');
    this.handleModalVisible();
  };

  handleSavePermission = (roleId, checkedKeys) => {
    const {dispatch} = this.props;
    const {currentPage, pageSize} = this.state;
    dispatch({
      type: 'role/saveRolePermission',
      payload: {
        roleId,
        permissionIds: checkedKeys,
        currentPage,
        pageSize,
      },
    });

    message.success('权限设置成功');
    this.handlePermissionWinVisible();
  };

  handleSelectRows = rows => {
    this.setState({
      selectedRows: rows,
    });
  };

  handleStandardTableChange = (pagination, filtersArg, sorter) => {
    const {dispatch} = this.props;
    const {formValues} = this.state;

    const filters = Object.keys(filtersArg).reduce((obj, key) => {
      const newObj = {...obj};
      newObj[key] = getValue(filtersArg[key]);
      return newObj;
    }, {});

    const params = {
      currentPage: pagination.current,
      pageSize: pagination.pageSize,
      ...formValues,
      ...filters,
    };
    if (sorter.field) {
      params.sorter = `${sorter.field}_${sorter.order}`;
    }

    this.setState({
      currentPage: pagination.current,
      pageSize: pagination.pageSize,
    });

    dispatch({
      type: 'role/fetch',
      payload: params,
    });
  };

  handleMenuClick = e => {
    const {dispatch} = this.props;
    const {selectedRows, currentPage, pageSize} = this.state;

    if (!selectedRows) return;
    switch (e.key) {
      case 'remove':
        dispatch({
          type: 'role/remove',
          payload: {
            selectedRows,
            currentPage,
            pageSize,
          },
          callback: () => {
            this.setState({
              selectedRows: [],
            });
          },
        });
        break;
      default:
        break;
    }
  };

  componentDidMount() {
    const {dispatch} = this.props;
    dispatch({
      type: 'role/fetch',
    });
    dispatch({
      type: 'role/fetchAllPermissions',
    });
  }

  render() {
    let {role: {data, allPermissions, permissionIds}, loading} = this.props;
    if (!data) {
      data = {};
    }
    const {selectedRows, modalVisible, action, formVal, permissionWinVisible, roleId} = this.state;
    const parentMethods = {
      handleSave: this.handleSave,
      handleModalVisible: this.handleModalVisible,
      handleSavePermission: this.handleSavePermission,
      handlePermissionWinVisible: this.handlePermissionWinVisible,
    };
    const defaultProps = {
      allPermissions,
      permissionIds,
      permissionWinVisible,
      roleId,
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
              <Button icon="plus" type="primary" onClick={() => this.handleModalVisible(true, "save")}>
                新建
              </Button>
              <Button icon="edit" type="primary" onClick={() => this.handleModalVisible(true, "update")}>
                更新
              </Button>
              <Button icon="edit" type="primary" onClick={() => this.handlePermissionWinVisible(true)}>
                权限
              </Button>
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
          <StandardTable
            selectedRows={selectedRows}
            loading={loading}
            data={data}
            columns={this.columns}
            onSelectRow={this.handleSelectRows}
            onChange={this.handleStandardTableChange}
          />
          <CreateForm {...parentMethods} modalVisible={modalVisible} action={action} values={formVal}/>
          <PermissionWin {...parentMethods} {...defaultProps} />
        </Card>
      </PageHeaderWrapper>
    )
  }
}
