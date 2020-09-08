const validateStub = {
  render: () => {},
  methods: {
    setContent: () => {}
  }
};

jest.unmock('axios');
import axios from 'axios';
import MockAdapter from "axios-mock-adapter";


import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import ActivityUpdatePanel from '@/components/article/ActivityUpdatePanel'
import Element from 'element-ui';
import moment from 'moment';

const localVue = createLocalVue();
localVue.use(Element);


describe('ActivityUpdatePanel.vue', () => {
  const wrapper = shallowMount(ActivityUpdatePanel, {
    localVue,
    stubs:{
      'Tinymce': validateStub
    },
    propsData:{
      updateContent: {
        activity: 0,
        activityImg : '0',
        activityName : '0',
        activityDescription : '0',
        type : 'true',
        start : '0',
      }
    }
  });

  beforeEach(() => {
    wrapper.vm.$nextTick(() => {});
  });

  let mockAdapter = new MockAdapter(axios);
  let spyPost = jest.spyOn(axios, "post");


  it('Activity Update Panel Rejects submitForm', async () => {
    wrapper.vm.limit = false;

    mockAdapter.onAny().reply(400, true);

    expect(spyPost).toHaveBeenCalledTimes(0);
    await wrapper.vm.submitForm();

    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(1);
    })
  });

  it('Activity Entity Panel Rejects confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;

    mockAdapter.onAny().reply(400, true);

    await wrapper.vm.confirmIdentity();

    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.confirmDelete).toBeFalsy();
      expect(spyPost).toHaveBeenCalledTimes(2);
    });
  });

  it('Activity Entity Panel Rejects deleteData',  async () => {
    wrapper.vm.confirmDelete = true;
    wrapper.vm.panelVisible = true;
    wrapper.vm.deleteVisible = true;

    mockAdapter.onAny().reply(400, true);

    await wrapper.vm.deleteData();

    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.panelVisible).toBeTruthy();
      expect(wrapper.vm.deleteVisible).toBeTruthy();
      expect(spyPost).toHaveBeenCalledTimes(3);
    });

  });

});
