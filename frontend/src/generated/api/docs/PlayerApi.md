# PlayerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**getPlayerByGameAndTagName**](#getplayerbygameandtagname) | **GET** /api/players/{gameName}/{tagName} | Get player by gameName and tagName|
|[**getPlayerById**](#getplayerbyid) | **GET** /api/players/{id} | Get player by PUUID|

# **getPlayerByGameAndTagName**
> PlayerDTO getPlayerByGameAndTagName()


### Example

```typescript
import {
    PlayerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new PlayerApi(configuration);

let gameName: string; //In game player name (default to undefined)
let tagName: string; //Player tag name (default to undefined)

const { status, data } = await apiInstance.getPlayerByGameAndTagName(
    gameName,
    tagName
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **gameName** | [**string**] | In game player name | defaults to undefined|
| **tagName** | [**string**] | Player tag name | defaults to undefined|


### Return type

**PlayerDTO**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | Player information |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **getPlayerById**
> PlayerDTO getPlayerById()


### Example

```typescript
import {
    PlayerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new PlayerApi(configuration);

let id: string; //Player uuid (default to undefined)

const { status, data } = await apiInstance.getPlayerById(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**string**] | Player uuid | defaults to undefined|


### Return type

**PlayerDTO**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | Player information |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

