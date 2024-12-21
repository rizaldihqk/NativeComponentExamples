import {requireNativeComponent, ViewProps} from 'react-native';
import {MarketData} from '../../types';

interface NativeComponentProps {
  data: MarketData[];
  refreshing: boolean;
}

type NativeListProps = ViewProps & NativeComponentProps;

export const NativeList = requireNativeComponent<NativeListProps>('nativeList');
