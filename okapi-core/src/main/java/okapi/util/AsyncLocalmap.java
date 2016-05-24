/*
 * Copyright (C) 2015 Index Data
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package okapi.util;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.shareddata.AsyncMap;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;

/**
 * Encapsulating vert.x LocalMap so it looks like a ClusterWideMap
 */
public class AsyncLocalmap<K, V> implements AsyncMap<K, V> {

  LocalMap<K, V> map = null;

  public AsyncLocalmap(Vertx vertx, String mapName) {
    SharedData sd = vertx.sharedData();
    this.map = sd.getLocalMap(mapName);
  }

  @Override
  public void get(K k, Handler<AsyncResult<V>> resultHandler) {
    V v = map.get(k); // null if not found
    resultHandler.handle(new Success<>(v));
  }

  @Override
  public void put(K k, V v, Handler<AsyncResult<Void>> completionHandler) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //map.put(k, v);
    //completionHandler.handle(new Success<>());
  }

  @Override
  public void put(K k, V v, long ttl, Handler<AsyncResult<Void>> completionHandler) {
    put(k, v, completionHandler);
  }

  @Override
  public void putIfAbsent(K k, V v, Handler<AsyncResult<V>> completionHandler) {
    V oldv = map.get(k); // null if not found
    if (oldv == null) {
      map.put(k, v);
      completionHandler.handle(new Success<>(null));
    } else {
      completionHandler.handle(new Success<>(oldv));
    }
  }

  @Override
  public void putIfAbsent(K k, V v, long ttl, Handler<AsyncResult<V>> completionHandler) {
    putIfAbsent(k, v, completionHandler);
  }

  @Override
  public void remove(K k, Handler<AsyncResult<V>> resultHandler) {
    // This is a unsafe operation!
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void removeIfPresent(K k, V v, Handler<AsyncResult<Boolean>> resultHandler) {
    V get = map.get(k);
    if (get.equals(v)) {
      map.remove(k);
      resultHandler.handle(new Success<>(true));
    } else {
      resultHandler.handle(new Success<>(false));
    }
  }

  @Override
  public void replace(K k, V v, Handler<AsyncResult<V>> resultHandler) {
    // This is a unsafe operation!
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void replaceIfPresent(K k, V oldValue, V newValue, Handler<AsyncResult<Boolean>> resultHandler) {
    V get = map.get(k);
    if (oldValue.equals(get)) {
      map.put(k, newValue);
      resultHandler.handle(new Success<>(true));
    } else {
      resultHandler.handle(new Success<>(false));
    }
  }

  @Override
  public void clear(Handler<AsyncResult<Void>> resultHandler) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void size(Handler<AsyncResult<Integer>> resultHandler) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
