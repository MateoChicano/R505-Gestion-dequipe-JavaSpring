<?php

function request(string $method, string $url, ?array $data = null, ?string $token = null): array {
    $headers = ['Accept: application/json'];
    if ($token) $headers[] = 'Authorization: Bearer '.$token;

    $options = [
        'http' => [
            'method' => $method,
            'header' => implode("\r\n", $headers),
            'ignore_errors' => true,
        ],
    ];

    if ($data !== null) {
        $headers[] = 'Content-Type: application/json';
        $options['http']['header'] = implode("\r\n", $headers);
        $options['http']['content'] = json_encode($data);
    }

    $context = stream_context_create($options);
    $response = @file_get_contents($url, false, $context);
    if ($response === false) {
        throw new Exception("Impossible d'appeler l'API : $url");
    }

    $statusCode = 0;
    $headers = function_exists('http_get_last_response_headers')
        ? (http_get_last_response_headers() ?? [])
        : [];
    if (isset($headers[0]) && preg_match('/HTTP\/\S+\s+(\d{3})/', $headers[0], $matches)) {
        $statusCode = (int) $matches[1];
    }

    return [
        'status_code' => $statusCode,
        'data' => json_decode($response, true),
    ];
}

function getRequest($url, $token = null)          { return request('GET', $url, null, $token); }
function postRequest($url, $data, $token = null)  { return request('POST', $url, $data, $token); }
function putRequest($url, $data, $token = null)   { return request('PUT', $url, $data, $token); }
function patchRequest($url, $data, $token = null) { return request('PATCH', $url, $data, $token); }
function deleteRequest($url, $token = null)       { return request('DELETE', $url, null, $token); }
