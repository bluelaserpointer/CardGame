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
import ActivityEditPanel from '@/components/article/ActivityEditPanel'
import Element from 'element-ui';
import moment from 'moment';

const localVue = createLocalVue();
localVue.use(Element);


describe('ActivityEditPanel.vue', () => {
  const wrapper = shallowMount(ActivityEditPanel, {
    localVue,
    stubs:{
      'Tinymce': validateStub
    }
  });

  beforeEach(() => {
    wrapper.vm.$nextTick(() => {});
  });

  let mockAdapter = new MockAdapter(axios);
  let spyPost = jest.spyOn(axios, "post");

  it('Activity Edit Panel Nulls created', async () => {
    expect(wrapper.vm.postForm.image_uri).toBe('');
    expect(wrapper.vm.postForm.title).toBe('');
    expect(wrapper.vm.limit).toBeFalsy();
    expect(wrapper.vm.displayTime).toBe(undefined);
    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.postForm.content).toBe('');
    })
  });

  it('Activity Edit Panel Nulls submitForm branch1', async () => {
    wrapper.vm.postForm.title = "TestTitle";
    wrapper.vm.postForm.content = "TestContent";
    wrapper.vm.postForm.image_uri = undefined;

    wrapper.vm.limit = false;

    mockAdapter.onAny().reply(200, false);

    await wrapper.vm.submitForm();
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(1);
    })
  });
});
