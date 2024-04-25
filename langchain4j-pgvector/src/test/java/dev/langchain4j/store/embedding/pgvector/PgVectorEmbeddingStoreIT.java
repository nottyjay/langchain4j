package dev.langchain4j.store.embedding.pgvector;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.AllMiniLmL6V2QuantizedEmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.*;
import dev.langchain4j.store.embedding.filter.Filter;
import dev.langchain4j.store.embedding.filter.comparison.*;
import dev.langchain4j.store.embedding.filter.logical.And;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Percentage.withPercentage;

@Testcontainers
public class PgVectorEmbeddingStoreIT extends EmbeddingStoreIT {

  @Container
  static PostgreSQLContainer<?> pgVector = new PostgreSQLContainer<>("pgvector/pgvector:pg15");

  EmbeddingStore<TextSegment> embeddingStore;

  EmbeddingModel embeddingModel = new AllMiniLmL6V2QuantizedEmbeddingModel();

  @BeforeEach
  void beforeEach() {
    embeddingStore = PgVectorEmbeddingStore.builder()
            .host(pgVector.getHost())
            .port(pgVector.getFirstMappedPort())
            .user("test")
            .password("test")
            .database("test")
            .table("test")
            .dimension(384)
            .dropTableFirst(true)
            .build();
  }

  @Override
  protected EmbeddingStore<TextSegment> embeddingStore() {
    return embeddingStore;
  }

  @Override
  protected EmbeddingModel embeddingModel() {
    return embeddingModel;
  }
}
