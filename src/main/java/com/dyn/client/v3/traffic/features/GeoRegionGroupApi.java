/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dyn.client.v3.traffic.features;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.Headers;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.annotations.Transform;

import com.dyn.client.v3.traffic.DynTrafficExceptions.JobStillRunningException;
import com.dyn.client.v3.traffic.domain.GeoRegionGroup;
import com.dyn.client.v3.traffic.filters.AlwaysAddContentType;
import com.dyn.client.v3.traffic.filters.AlwaysAddUserAgent;
import com.dyn.client.v3.traffic.filters.SessionManager;
import com.dyn.client.v3.traffic.functions.ExtractLastPathComponent;
import com.google.common.collect.FluentIterable;

/**
 * @see <a
 *      href="https://manage.dynect.net/help/docs/api2/rest/resources/Geo.html"
 *      />
 * @author Adrian Cole
 */
@Headers(keys = "API-Version", values = "{jclouds.api-version}")
@Path("/GeoRegionGroup/{serviceName}")
@RequestFilters({ AlwaysAddUserAgent.class, AlwaysAddContentType.class, SessionManager.class })
@Deprecated
public interface GeoRegionGroupApi {
    /**
     * Lists all geo region group names.
     * 
     * @throws JobStillRunningException
     *            if a different job in the session is still running
     */
    @Named("ListGeoRegionGroupNames")
    @GET
    @SelectJson("data")
    @Transform(ExtractLastPathComponent.class)
    FluentIterable<String> list() throws JobStillRunningException;

    /**
     * Retrieves information about the specified geo region group
     * 
     * @param groupName
     *           name of the group to get information about. ex
     *           {@code api-prod}
     * @return null if not found
     * @throws JobStillRunningException
     *            if a different job in the session is still running
     */
    @Named("GetGeoRegionGroup")
    @GET
    @Path("/{groupName}")
    @SelectJson("data")
    @Fallback(NullOnNotFoundOr404.class)
    @Nullable
    GeoRegionGroup get(@PathParam("groupName") String groupName) throws JobStillRunningException;
}
