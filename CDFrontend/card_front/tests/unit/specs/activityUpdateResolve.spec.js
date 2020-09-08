import Vuex from "vuex";

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
localVue.use(Vuex);

let store = new Vuex.Store();

describe('ActivityUpdatePanel.vue', () => {
  const wrapper = shallowMount(ActivityUpdatePanel, {
    store,
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

  it('Activity Update Panel Resolves created', async () => {
    expect(wrapper.vm.postForm.image_uri).toBe('0');
    expect(wrapper.vm.postForm.title).toBe('0');
    expect(wrapper.vm.limit).toBeTruthy();
    expect(wrapper.vm.displayTime).toBe('0');
    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.postForm.content).toBe('0');
    })
  });


  it('Activity Entity Panel Resolves confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;
    mockAdapter.onAny().reply(200, true);

    await wrapper.vm.confirmIdentity();
  });

  it('Activity Entity Panel Resolves confirmIdentity Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.confirmDelete).toBeTruthy();
      expect(spyPost).toHaveBeenCalledTimes(1);
    });
  });

  it('Activity Entity Panel Resolves deleteData',  async () => {
    wrapper.vm.confirmDelete = true;

    mockAdapter.onAny().reply(200, true);

    await wrapper.vm.deleteData();
  });

  it('Activity Entity Panel Resolves deleteData Result',  () => {
    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.panelVisible).toBeFalsy();
      expect(wrapper.vm.deleteVisible).toBeFalsy();
      expect(spyPost).toHaveBeenCalledTimes(2);
    });
  });

  it('Activity Update Panel Resolves submitForm branch1', async () => {
    wrapper.vm.postForm.title = "TestTitle";
    wrapper.vm.postForm.content = "TestContent";
    wrapper.vm.postForm.image_uri = undefined;

    wrapper.vm.limit = false;
    await wrapper.vm.submitForm();
    wrapper.vm.$nextTick(()=>{
      expect(spyPost).toHaveBeenCalledTimes(3);
    })
  });

  it('Activity Update Panel Resolves submitForm branch2', async() => {
    wrapper.vm.limit = true;
    wrapper.vm.displayTime = '2000-01-01 00:00:00';
    await wrapper.vm.submitForm();
    wrapper.vm.$nextTick(()=>{
      expect(spyPost).toHaveBeenCalledTimes(4);
    })  });

  it('Activity Update Panel Resolves submitForm branch3', async() => {
    wrapper.vm.limit = true;
    wrapper.vm.displayTime = undefined;

    await wrapper.vm.submitForm();
    wrapper.vm.$nextTick(()=>{
      expect(spyPost).toHaveBeenCalledTimes(5);
    })  });

  it('Activity Update Panel Resolves submitForm branch4', async () => {
    wrapper.vm.postForm.image_uri = undefined;
    wrapper.vm.postForm.title = undefined;
    wrapper.vm.postForm.content = undefined;

    mockAdapter.onAny().reply(200, true);

    await wrapper.vm.submitForm();
  });

  it('Activity Update Panel Resolves submitForm branch4 Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(6);
    });
  });


  it('Activity Update Panel Resolves formatDate', () => {
    let date = new Date();
    let formatDateStr = moment(wrapper.vm.formatDate(date)).format('YYYY-MM-DD HH:mm:ss');
    expect(wrapper.vm.formatDate(date)).toBe(formatDateStr);
  });


  it('Activity Update Panel Resolves submitForm Rest', () => {
    wrapper.vm.updateContent = {activityImg: 'Img', activityName: 'Name', activityDescription: 'Description', type: 'true'};
    wrapper.vm.deleteVisible = true;
    wrapper.vm.$nextTick(() => {
      done();
    });

    wrapper.vm.updateContent = {activityImg: 'Img', activityName: 'Name', type: 'false'};
    wrapper.vm.$nextTick(() => {
      done();
    });

    wrapper.vm.setTagsViewTitle();
    wrapper.vm.setPageTitle();
  });

});
