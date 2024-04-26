resource "digitalocean_kubernetes_cluster" "poly-yard-app" {
  name    = var.application_name
  region  = var.k8s_region
  version = var.k8s_version
  node_pool {
    name       = "poly-yard-worker-pool"
    size       = var.k8s_size
    node_count = var.k8s_node_count
  }
}

data "kubectl_path_documents" "docs" {
  pattern = "${path.cwd}/../k8s/*.yml"
}

resource "kubectl_manifest" "backend-app" {
  for_each  = toset(data.kubectl_path_documents.docs.documents)
  yaml_body = each.value
}
