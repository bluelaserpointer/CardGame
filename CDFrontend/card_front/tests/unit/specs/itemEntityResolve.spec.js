let mockData = { result: [{
    itemId: 0,
    itemName: '',
    price: 0,
    itemDetails: {
      itemImg: 'testImg',
      itemDescription: 'testItemText',
    }
  }]};

let mockDataTrans = [{
  itemId: 0,
  itemName: '',
  price: 0,
  itemImg: 'testImg',
  itemDescription: 'testItemText',
  itemDetails: {
    itemImg: 'testImg',
    itemDescription: 'testItemText',
  }
}];

const validateStub = {
  render: () => {},
  methods: {
    validate: () => {}
  }
};

jest.unmock('axios');
import axios from 'axios';
import MockAdapter from "axios-mock-adapter";

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import ItemEntityPanel from '@/components/table/itemEntityPanel'
import Element from 'element-ui';

const localVue = createLocalVue();
localVue.use(Element);

describe('ItemEntityPanel.vue', () => {
  const wrapper = shallowMount(ItemEntityPanel,
    {
      localVue,
      stubs:{
        'el-form': validateStub
      }
    });

  let mockAdapter = new MockAdapter(axios);
  let spyPost = jest.spyOn(axios, "post");

  mockAdapter.onPost('item/List').reply(200, mockData);

  it('Startup', async () => {
    await wrapper.vm.getList();
  });

  it('Item Entity Panel Resolves created',   async () => {
    expect(wrapper.vm.list).toStrictEqual(mockDataTrans);

    expect(spyPost).toHaveBeenCalledTimes(1);
  });

  it('Item Entity Panel Resolves confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;
    mockAdapter.onPost('user/confirmDelete').reply(200, true);
    await wrapper.vm.confirmIdentity();
  });

  it('Item Entity Panel Resolves confirmIdentity result', async () => {
    expect(wrapper.vm.confirmDelete).toBeTruthy();
    expect(spyPost).toHaveBeenCalledTimes(2);
  });

  it('Item Entity Panel Resolves deleteData',  async () => {
    wrapper.vm.confirmDelete = true;
    mockAdapter.onPost('item/deleteItem').reply(200, true);
    await wrapper.vm.deleteData();
  });

  it('Item Entity Panel Resolves deleteData result', async () => {
    expect(wrapper.vm.panelVisible).toBeFalsy();
    expect(wrapper.vm.deleteVisible).toBeFalsy();
    expect(spyPost).toHaveBeenCalledTimes(4);
  });

  it('Item Entity Panel Resolves resetTemp', () => {
    wrapper.vm.temp = {
      itemId: 1,
      itemName: '1',
      price: 1,
      itemImg: '1',
      itemDescription: '1'
    };

    wrapper.vm.resetTemp();
    expect(wrapper.vm.temp).toStrictEqual(
      {
        itemId: undefined,
        itemName: 'New Item',
        price: 999,
        itemImg: '',
        itemDescription: 'No description yet.'
      }
    );
  });

  it('Item Entity Panel Resolves handleCreate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleCreate();
    expect(wrapper.vm.dialogStatus).toBe('create');
    expect(wrapper.vm.dialogStatus).toBeTruthy();
  });

  it('Item Entity Panel Resolves handleUpdate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleUpdate();
    expect(wrapper.vm.dialogStatus).toBe('update');
    expect(wrapper.vm.dialogStatus).toBeTruthy();
  });

  it('Item Entity Panel Resolves createData', async () => {
    wrapper.vm.panelVisible = true;
    mockAdapter.onAny().reply(200, true);
    wrapper.vm.createData('temp');
    await wrapper.vm.submitCreate();
  });

  it('Item Entity Panel Resolves createData result',  () => {
    expect(spyPost).toHaveBeenCalledTimes(6);
  });

  it('Item Entity Panel Resolves updateData', async () => {
    wrapper.vm.panelVisible = true;
    wrapper.vm.updateData('temp');
    await wrapper.vm.submitUpdate();
  });

  it('Item Entity Panel Resolves updateData result',  () => {
    expect(spyPost).toHaveBeenCalledTimes(8);
  });

  it('Item Entity Panel Resolves Rest',  () => {
    wrapper.vm.handleFilter();
    wrapper.vm.sortChange({prop: 'id'});
    wrapper.vm.sortByID('ascending');
  });
});
