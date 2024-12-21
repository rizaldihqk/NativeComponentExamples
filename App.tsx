import {DeviceEventEmitter, StyleSheet} from 'react-native';
import React, {useEffect, useState} from 'react';
import {NativeList} from './nativeComponents/list';
import {MarketData} from './types';
import {MarketDataDummy} from './Mock/mockData';
import {useInterval} from './hooks';
const styles = StyleSheet.create({
  full: {
    flex: 1,
  },
});

const App = () => {
  const [refreshing, setRefreshing] = useState(false);
  const [marketData, setMarketData] = useState<MarketData[]>([]);

  useEffect(() => {
    getProduct();
    DeviceEventEmitter.addListener('refreshFeed', () => getProduct());
    DeviceEventEmitter.addListener('callback', i => onFavToogle(i));

    return () => {
      DeviceEventEmitter.removeAllListeners();
    };
  }, []);

  const getProduct = async () => {
    setRefreshing(true);
    try {
      const response = MarketDataDummy;
      setMarketData(response);
    } catch (error) {
      console.log({error});
    }
    setRefreshing(false);
  };

  useInterval(async () => {
    const newArr = marketData.map(e => ({
      ask: (Number(e.ask) + 1).toString(),
      bid: (Number(e.bid) + 1).toString(),
      price: (Number(e.price) + 1).toString(),
      favorite: e.favorite,
      id: e.id,
    }));

    setMarketData(newArr);
  }, 500);

  const onFavToogle = (dataEmitter: {data: number}) => {
    const key = Object.keys(dataEmitter.data);
    const datatest = Object.values(dataEmitter.data);

    let newArr = JSON.parse(datatest[0]);

    newArr[key[0]].favorite = !newArr[key[0]].favorite;

    setMarketData(newArr);
  };

  return (
    <NativeList data={marketData} refreshing={refreshing} style={styles.full} />
  );
};

export default App;
