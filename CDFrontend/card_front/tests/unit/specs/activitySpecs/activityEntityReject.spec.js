jest.mock('axios', () => ({
  get: jest.fn(() => Promise.reject()),
  post: jest.fn(() => Promise.reject())
}));

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import ActivityEntityPanel from '@/components/edit/ActivityEntityPanel'
import Element from 'element-ui';
import moment from 'moment';
import axios from 'axios';

const localVue = createLocalVue();
localVue.use(Element);

describe('ActivityEntityPanel.vue', () => {
  const wrapper = shallowMount(ActivityEntityPanel,
    {
      localVue
    });

  it('Activity Entity Panel Rejects created getList watchList', async () => {
    expect(wrapper.vm.panelVisible).toBeFalsy();
    expect(wrapper.vm.list).toStrictEqual(null);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toBeCalledWith('http://localhost:8080/activity/getAllActivities');
  });
});
